//package com.exe01.backend.Vnpay;
//
//import com.exe01.backend.dto.request.transaction.BaseTransactionRequest;
//import com.exe01.backend.exception.BaseException;
//import jakarta.servlet.http.HttpServletRequest;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.servlet.view.RedirectView;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@Service
//public class PayService {
//
//    Logger logger = LoggerFactory.getLogger(PayService.class);
//
//    //@Value("${react.frontend.host}")
//private String frontEndHost = "a";
//
//    @Autowired
//    ITransactionService transactionService;
//
//    @Autowired
//    IAccountService accountService;
//
//    public String payWithVNPAY(BaseTransactionRequest transactionDTO, HttpServletRequest request) throws UnsupportedEncodingException, BaseException{
//        logger.info("Start Payment");
//        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
//        logger.info("cld: {}", cld);
//        logger.info("cld: {}", cld);
//
//
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//        String vnp_CreateDate = formatter.format(cld.getTime());
//
//        logger.info("vnp_CreateDate: {}", vnp_CreateDate);
//
//        transactionDTO.vnp_TxnRef = Config.getRandomNumber(8);
//
//        cld.add(Calendar.MINUTE, 2);
//
//        String vnp_ExpireDate = formatter.format(cld.getTime());
//
//        logger.info("vnp_Exp: {}", vnp_ExpireDate);
//
//        TransactionDTO transactionDTO_db =  transactionService.create(transactionDTO);
//
//
//        Map<String, String> vnp_Params = new HashMap<>();
//        vnp_Params.put("vnp_Version", VnPayConstant.vnp_Version);
//        vnp_Params.put("vnp_Command", VnPayConstant.vnp_Command);
//        vnp_Params.put("vnp_TmnCode", VnPayConstant.vnp_TmnCode);
//        vnp_Params.put("vnp_Amount", String.format("%.0f", transactionDTO.getAmount()*100));
//        vnp_Params.put("vnp_BankCode", VnPayConstant.vnp_BankCode);
//        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
//        vnp_Params.put("vnp_CurrCode", VnPayConstant.vnp_CurrCode);
//        vnp_Params.put("vnp_IpAddr", Config.getIpAddress(request));
//        vnp_Params.put("vnp_Locale", VnPayConstant.vnp_Locale);
//        vnp_Params.put("vnp_OrderInfo", transactionDTO.vnp_OrderInfo);
//        vnp_Params.put("vnp_OrderType", transactionDTO.vnp_OrderType);
//        vnp_Params.put("vnp_ReturnUrl", VnPayConstant.vnp_ReturnUrl);
//        vnp_Params.put("vnp_TxnRef", String.valueOf(transactionDTO.getPoints().toString()+"/"+transactionDTO.getAccountId())+"/" + transactionDTO_db.getId() + String.valueOf(transactionDTO.vnp_TxnRef));
//        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
//
//        List fieldList = new ArrayList(vnp_Params.keySet());
//        Collections.sort(fieldList);
//
//        StringBuilder hashData = new StringBuilder();
//        StringBuilder query = new StringBuilder();
//
//        Iterator itr = fieldList.iterator();
//        while (itr.hasNext()) {
//            String fieldName = (String) itr.next();
//            String fieldValue = vnp_Params.get(fieldName);
//            if (fieldValue != null && (fieldValue.length() > 0)) {
//                hashData.append(fieldName);
//                hashData.append("=");
//                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//
//                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
//                query.append("=");
//                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//
//                if (itr.hasNext()) {
//                    query.append("&");
//                    hashData.append("&");
//                }
//            }
//        }
//        String queryUrl = query.toString();
//        String vnp_SecureHash = Config.hmacSHA512(VnPayConstant.vnp_HashSecret, hashData.toString());
//        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
//        String paymentUrl = VnPayConstant.vnp_Url + "?" + queryUrl;
//
//        return paymentUrl;
//    }
//
//    public RedirectView handleTransaction() {
//        // Redirect to the specified URL when the condition is met
//        return new RedirectView("http://localhost:8086/paid-success");
//
//    }
//
//    public RedirectView handleTransactionForPoint(Double amount, String bankCode, String responseCode, String txnRef) throws BaseException {
//        if ("00".equals(responseCode)) {
//            // Trạng thái thành công
//            Integer points = Integer.valueOf(extractValue(txnRef, 1));
//            UUID accountId = UUID.fromString(extractValue(txnRef, 2));
//            accountService.updatePoint(accountId, points);
//
//            return new RedirectView(frontEndHost+ "user/history");
//        } else {
//            UUID transactionId = UUID.fromString(extractValue(txnRef, 3));
//            transactionService.changeStatus(transactionId);
//            // Trạng thái thất bại
//            return new RedirectView(frontEndHost+ "pay-failed");
//        }
//    }
//
//    private  String extractValue(String input, int groupIndex) {
//        // Regex pattern to match "20/cc58180a-3d5f-48bd-ba18-3d229eb2e9d4/eb785d44-ead0-4ad7-b072-e2d23c50999d58860650"
//        Pattern pattern = Pattern.compile("(\\d+)/(\\w+-\\w+-\\w+-\\w+-\\w+)/(\\w+-\\w+-\\w+-\\w+-\\w+)");
//        Matcher matcher = pattern.matcher(input);
//
//        if (matcher.find()) {
//            return matcher.group(groupIndex);
//        } else {
//            return "Value not found"; // Handle cases where the pattern doesn't match
//        }
//    }
//}
