package com.exe01.backend.Vnpay;

import com.exe01.backend.constant.ConstStatus;
import com.exe01.backend.dto.request.transaction.BaseTransactionRequest;
import com.exe01.backend.dto.request.transaction.PaymentRequest;
import com.exe01.backend.entity.Payment;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.service.IPaymentService;
import com.exe01.backend.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PayService {

    Logger logger = LoggerFactory.getLogger(PayService.class);
    @Autowired
    IPaymentService paymentService;

    @Autowired
    IUserService userService;

    //@Value("${react.frontend.host}")
private String frontEndHost = "a";


    public String payWithVNPAY(PaymentRequest transactionDTO, HttpServletRequest request) throws UnsupportedEncodingException, BaseException{
        logger.info("Start Payment");
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        logger.info("cld: {}", cld);
        logger.info("cld: {}", cld);


        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        logger.info("vnp_CreateDate: {}", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 2);
        cld.add(Calendar.HOUR, 7);

        String vnp_ExpireDate = formatter.format(cld.getTime());

        logger.info("vnp_Exp: {}", vnp_ExpireDate);

        Payment payment = paymentService.create(transactionDTO);


        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VnPayConstant.vnp_Version);
        vnp_Params.put("vnp_Command", VnPayConstant.vnp_Command);
        vnp_Params.put("vnp_TmnCode", VnPayConstant.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.format("%.0f", transactionDTO.getTotal()*100));
        vnp_Params.put("vnp_BankCode", VnPayConstant.vnp_BankCode);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        vnp_Params.put("vnp_CurrCode", VnPayConstant.vnp_CurrCode);
        vnp_Params.put("vnp_IpAddr", Config.getIpAddress(request));
        vnp_Params.put("vnp_Locale", VnPayConstant.vnp_Locale);
        vnp_Params.put("vnp_OrderInfo", transactionDTO.vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", transactionDTO.vnp_OrderType);
        vnp_Params.put("vnp_ReturnUrl", VnPayConstant.vnp_ReturnUrl);
        vnp_Params.put("vnp_TxnRef", String.valueOf(transactionDTO.getTierId()+"/"+transactionDTO.getUserId())+"/" + payment.getId() );
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldList = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldList);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        Iterator itr = fieldList.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append("=");
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append("=");
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                if (itr.hasNext()) {
                    query.append("&");
                    hashData.append("&");
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(VnPayConstant.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VnPayConstant.vnp_Url + "?" + queryUrl;

        return paymentUrl;
    }

    public RedirectView handleTransaction() {
        // Redirect to the specified URL when the condition is met
        return new RedirectView("http://localhost:8086/paid-success");

    }

    public RedirectView handleTransactionForPoint(Double amount, String bankCode, String responseCode, String txnRef) throws BaseException {
        UUID paymentId = UUID.fromString(extractValue(txnRef, 3));
        if ("00".equals(responseCode)) {
            // Trạng thái thành công
            UUID subcriptionId = UUID.fromString(extractValue(txnRef, 1));
            UUID accountId = UUID.fromString(extractValue(txnRef, 2));
            userService.updateReviewCVTimes(accountId, subcriptionId);
            paymentService.changeStatus(ConstStatus.TransactionStatus.SUCCESS_STATUS ,paymentId);
            return new RedirectView(frontEndHost+ "/payment/success");
        } else {
            paymentService.changeStatus(ConstStatus.TransactionStatus.FAILED_STATUS ,paymentId);
            // Trạng thái thất bại
            return new RedirectView(frontEndHost+ "/payment/failed");
        }
    }

    private  String extractValue(String input, int groupIndex) {
        // Regex pattern to match "20/cc58180a-3d5f-48bd-ba18-3d229eb2e9d4/eb785d44-ead0-4ad7-b072-e2d23c50999d58860650"
        Pattern pattern = Pattern.compile("(\\w+-\\w+-\\w+-\\w+-\\w+)/(\\w+-\\w+-\\w+-\\w+-\\w+)/(\\w+-\\w+-\\w+-\\w+-\\w+)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(groupIndex);
        } else {
            return "Value not found"; // Handle cases where the pattern doesn't match
        }
    }
}
