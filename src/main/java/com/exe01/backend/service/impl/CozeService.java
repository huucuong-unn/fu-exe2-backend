package com.exe01.backend.service.impl;

import com.exe01.backend.constant.Const;
import com.exe01.backend.constant.ConstError;
import com.exe01.backend.dto.request.coze.CozeCreateChatRequest;
import com.exe01.backend.dto.request.coze.CozeCreateCoverLetterRequest;
import com.exe01.backend.dto.response.coze.*;
import com.exe01.backend.enums.ErrorCode;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.openfeign.CozeClient;
import com.exe01.backend.service.ICozeService;
import com.exe01.backend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CozeService implements ICozeService {


    @Autowired
    CozeClient cozeClient;

    @Autowired
    IUserService userService;
    String authorizationToken = "Bearer pat_xV7buJDamtEZutBEYt0IWIYxynsGja7M266UzP2j6Bp56loxmOEVDhUGM5M8kpQy";


    @Override
    public CozeCreateChatResponse createChat(String fileId) {
        CozeCreateChatRequest chatRequest = new CozeCreateChatRequest();
        // init file id
        CozeCreateChatRequest.AdditionalMessage additionalMessage = new CozeCreateChatRequest.AdditionalMessage(fileId);
        List<CozeCreateChatRequest.AdditionalMessage> additionalMessages = new ArrayList<>();
        additionalMessages.add(additionalMessage);
        chatRequest.setAdditionalMessages(additionalMessages);
        // Create a chat with the uploaded file
        return cozeClient.createChat(authorizationToken, chatRequest);
    }

    @Override
    public CozeFeedbackResponse uploadFile(MultipartFile file, UUID userId) throws BaseException {
        try {
            boolean isUserSubscriptionAvailable = userService.isUserSubscriptionActive(userId, new Date());

            if (!isUserSubscriptionAvailable) {
                throw new BaseException(200, ConstError.Coze.NO_LONGER_VALID, ErrorCode.ERROR_406.getMessage());
            }

            //check remain review cv
            userService.checkRemainReviewCV(userId, Const.CV);
            // Define the authorization token
            // Upload the file
            CozeUploadFileResponse fileResponse = cozeClient.uploadFile(authorizationToken, file);
//        // Create chat
            CozeCreateChatResponse chatResponse = createChat(fileResponse.getData().getId());
            //get message list
            List<CozeMessageListResponse.ChatData> messages;
            do {
                CozeMessageListResponse messageListResponse = cozeClient.getMessageList(authorizationToken, chatResponse.getData().getConversationId());
                messages = messageListResponse.getData();
                if (messages.size() < 2) {
                    Thread.sleep(1000); // Sleep for 1 second before retrying
                } else {
                    Thread.sleep(5000); // Sleep for 10 second before retrying
                    messageListResponse = cozeClient.getMessageList(authorizationToken, chatResponse.getData().getConversationId());
                    messages = messageListResponse.getData();
                }
            } while (messages.size() < 2);        // Parse the feedback data
            userService.updateReviewCVTimes(userId, null,Const.CV);
            CozeFeedbackResponse feedbackResponse =  parseFeedbackData(messages.get(0).getContent());
            feedbackResponse.setUserId(userId);
            return feedbackResponse;
        } catch (Exception e) {
            if (BaseException.class.isInstance(e)) {
                throw (BaseException) e;
            }
            throw new BaseException(500, "Failed to upload file or fetch messages", "ERROR");
        }
    }

    @Override
    public CozeCreateCoverLetterResponse CreateCoverLeter(CozeCreateCoverLetterRequest request) throws BaseException {
        List<String> result = new ArrayList<>();
        CozeCreateCoverLetterResponse response = new CozeCreateCoverLetterResponse();
        try {
            boolean isUserSubscriptionAvailable = userService.isUserSubscriptionActive(request.getUserId(), new Date());

            if (!isUserSubscriptionAvailable) {
                throw new BaseException(200, ConstError.Coze.NO_LONGER_VALID, ErrorCode.ERROR_406.getMessage());
            }

            //check remain review cv
            userService.checkRemainReviewCV(request.getUserId(),Const.COVER_LETTER);
            //find user
            // Define the authorization token
            // Create a chat with the uploaded file
            CozeCreateChatRequest chatRequest = new CozeCreateChatRequest();
            CozeCreateChatRequest.AdditionalMessage additionalMessage = new CozeCreateChatRequest.AdditionalMessage(request);
            chatRequest.setAdditionalMessages(List.of(additionalMessage));
            CozeCreateChatResponse chatResponse = cozeClient.createChat(authorizationToken, chatRequest);
            //get message list
            List<CozeMessageListResponse.ChatData> messages;
            do {
                CozeMessageListResponse messageListResponse = cozeClient.getMessageList(authorizationToken, chatResponse.getData().getConversationId());
                messages = messageListResponse.getData();
                if (messages.size() < 2) {
                    Thread.sleep(1000); // Sleep for 1 second before retrying
                } else {
                    Thread.sleep(5000); // Sleep for 10 second before retrying
                    messageListResponse = cozeClient.getMessageList(authorizationToken, chatResponse.getData().getConversationId());
                    messages = messageListResponse.getData();
                }
            } while (messages.size() < 2);        // Parse the feedback data
            String[] lines = (messages.get(0).getContent()).replace("*","").split("\n");
            for (int i = 0; i < lines.length; i++) {
                // Lưu các phần cần thiết vào danh sách
                if(i==0){
                    continue;
                }
                if ( (lines[i].contains("Date and contact information") || lines[i].contains("Opening paragraph") ||
                        lines[i].contains("Middle paragraph(s)") || lines[i].contains("Closing paragraph") || lines[i].contains("Complimentary close and signature") )
                        || lines[i].equals("") ){
                 continue;
                }else{
                    result.add(lines[i]);
                }


            }
            userService.updateReviewCVTimes(request.getUserId(), null, Const.COVER_LETTER);
            response.setData(result);
            response.setUserId(request.getUserId());
            return  response;
        } catch (Exception e) {
            if (BaseException.class.isInstance(e)) {
                throw (BaseException) e;
            }
            throw new BaseException(500, "Failed to create cover letter", "ERROR");
        }
    }
    public static List<CozeCreateInterviewQuestionResponse.InterviewQuestion> parseQuestions(String input) {
        List<CozeCreateInterviewQuestionResponse.InterviewQuestion> questionsList = new ArrayList<>();

        // Split the input by **Q:** to get the individual question blocks
        String[] questionBlocks = input.split("\\*\\*Q:\\*\\*");

        for (String block : questionBlocks) {
            // Ignore the first block as it will be empty
            if (block.trim().isEmpty()) continue;

            // Split each block by **A:** to separate question and answer
            String[] parts = block.split("\\*\\*A:\\*\\*");
            if (parts.length == 2) {
                String question = parts[0].trim();
                String answer = parts[1].trim();
                questionsList.add(new CozeCreateInterviewQuestionResponse.InterviewQuestion(question, answer));
            }
        }

        return questionsList;
    }

    public CozeFeedbackResponse parseFeedbackData(String rawData) {
        CozeFeedbackResponse response = new CozeFeedbackResponse();
        List<CozeFeedbackResponse.Correction> spellingCorrections = new ArrayList<>();
        List<CozeFeedbackResponse.Sentence> sentencesToImprove = new ArrayList<>();
        List<String> suitablePositions = new ArrayList<>();

        // Split the raw data into lines
        String rawDataRemove = rawData.replace("*", "");
        rawDataRemove = rawDataRemove.replace("#", "");
        String[] lines = rawDataRemove.split("\n");

        // Temporary storage
        String currentSection = "";

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            // Determine section
            if (line.startsWith("Head1 -")) {
                currentSection = "Head1";
            } else if (line.startsWith("Head2 -")) {
                currentSection = "Head2";
            } else if (line.startsWith("Head3 -")) {
                currentSection = "Head3";
            } else if (line.startsWith("spe")) {
                if ("Head1".equals(currentSection)) {
                    parseSpellingCorrection(line, spellingCorrections);
                }
            } else if (line.startsWith("sent")) {
                if ("Head2".equals(currentSection)) {
                    parseSentenceToImprove(line, sentencesToImprove);
                }
            } else if (line.startsWith("sp")) {
                if ("Head3".equals(currentSection)) {
                    suitablePositions.add(line.substring(line.indexOf("-") + 1).trim());
                }
            }
        }

        // Set parsed data into response
        response.setSpelling(spellingCorrections);
        response.setSentences(sentencesToImprove);
        response.setPositions(suitablePositions);

        return response;
    }

    private void parseSpellingCorrection(String line, List<CozeFeedbackResponse.Correction> corrections) {
        // Example: spe1 - "Builted" should be "Built" in the "FPT Software HCM" section.
        String[] parts = line.split(" - ");
        if (parts.length == 2) {
            String correctionText = parts[1].trim();
            String[] correctionParts = correctionText.split(" should be ");
            if (correctionParts.length == 2) {
                String incorrectWord = correctionParts[0].replace("\"", "").trim();
                String correctPart = correctionParts[1];
                String[] correctParts = correctPart.split(" in the ");
                if (correctParts.length == 2) {
                    CozeFeedbackResponse.Correction correction = new CozeFeedbackResponse.Correction();
                    correction.setIncorrect(incorrectWord);
                    correction.setCorrect(correctParts[0].replace("\"", "").trim());
                    corrections.add(correction);
                }
            }
        }
    }

    private void parseSentenceToImprove(String line, List<CozeFeedbackResponse.Sentence> sentences) {
        // Example: sent1 - In the "FPT Software HCM" section, the sentence "...", could be rephrased for better flow. Perhaps, "..."
        String[] parts = line.split(" - ", 2);
        if (parts.length >= 2) {
            String originalText = parts[1].trim();
            String[] rephrasedParts = originalText.split("Perhaps, ");
            if (rephrasedParts.length == 2) {
                CozeFeedbackResponse.Sentence sentence = new CozeFeedbackResponse.Sentence();
                sentence.setOriginal(rephrasedParts[0].replace("\"", "").trim());
                sentence.setRevised(rephrasedParts[1].replace("\"", "").trim());
                sentences.add(sentence);
            }
        }
    }

}