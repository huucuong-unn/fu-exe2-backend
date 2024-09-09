package com.exe01.backend.service.impl;
import com.exe01.backend.dto.request.coze.CozeCreateChatRequest;
import com.exe01.backend.dto.response.coze.CozeCreateChatResponse;
import com.exe01.backend.dto.response.coze.CozeFeedbackResponse;
import com.exe01.backend.dto.response.coze.CozeMessageListResponse;
import com.exe01.backend.dto.response.coze.CozeUploadFileResponse;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.openfeign.CozeClient;
import com.exe01.backend.service.ICozeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class CozeService implements ICozeService {


    @Autowired
     CozeClient cozeClient;
    String authorizationToken = "Bearer pat_ruWxZd3Z4y2l6Qp8iFbydh7GtMxG6Zm1njHvY7OTxkMymNFHJL5l5O90ZUW9Frq3";


    @Override
    public CozeCreateChatResponse createChat(String fileId) {
        CozeCreateChatRequest chatRequest = new CozeCreateChatRequest();
        // init file id
        CozeCreateChatRequest.AdditionalMessage additionalMessage = new CozeCreateChatRequest.AdditionalMessage(fileId);
        List<CozeCreateChatRequest.AdditionalMessage> additionalMessages = new ArrayList<>();
        additionalMessages.add(additionalMessage);
        chatRequest.setAdditionalMessages(additionalMessages);
        // Create a chat with the uploaded file
         return  cozeClient.createChat(authorizationToken,chatRequest);
    }

    @Override
    public CozeFeedbackResponse uploadFile(MultipartFile file) throws BaseException{
        try {
        // Define the authorization token
        // Upload the file
        CozeUploadFileResponse fileResponse =  cozeClient.uploadFile(authorizationToken,file);
//        // Create chat
        CozeCreateChatResponse chatResponse =  createChat(fileResponse.getData().getId());
        //get message list
        List<CozeMessageListResponse.ChatData> messages;
        do {
            CozeMessageListResponse messageListResponse = cozeClient.getMessageList(authorizationToken, chatResponse.getData().getConversationId());
            messages = messageListResponse.getData();
            if (messages.size() < 2) {
                Thread.sleep(1000); // Sleep for 1 second before retrying
            }else{
                Thread.sleep(5000); // Sleep for 10 second before retrying
                messageListResponse = cozeClient.getMessageList(authorizationToken, chatResponse.getData().getConversationId());
                messages = messageListResponse.getData();
            }
        } while (messages.size() < 2);        // Parse the feedback data
        return parseFeedbackData(messages.get(0).getContent());
        } catch (Exception e) {
            throw new BaseException(500, "Failed to upload file or fetch messages", "ERROR");
        }
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

