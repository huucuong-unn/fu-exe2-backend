package com.exe01.backend.service.impl;

import com.exe01.backend.PayOs.CreatePaymentLinkRequestBody;
import com.exe01.backend.constant.ConstStatus;
import com.exe01.backend.dto.request.transaction.PaymentRequest;
import com.exe01.backend.entity.Payment;
import com.exe01.backend.entity.Subscription;
import com.exe01.backend.service.IPayOsService;
import com.exe01.backend.service.IPaymentService;
import com.exe01.backend.service.ISubscriptionService;
import com.exe01.backend.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;
import vn.payos.type.PaymentLinkData;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class PayOsService implements IPayOsService {
    private final PayOS payOS;

    public PayOsService(PayOS payOS) {
        super();
        this.payOS = payOS;
    }

    @Value("${tortee.backend.host}")
    private String backEndHost;

    @Value("${react.frontend.host}")
    private String frontEndHost;
    @Autowired
    private ISubscriptionService subscriptionService;

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    IUserService userService;

    @Override
    public ObjectNode createPaymentLink(CreatePaymentLinkRequestBody RequestBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            Subscription subscription = subscriptionService.findByPlanType(RequestBody.getProductName());

            final String description = subscription.getPlanType() + ".";
            String returnUrl = backEndHost + "/api/v1/order/handle-order-status/";
            String cancelUrl = "";
            final int price = Integer.parseInt(subscription.getPrice().toString().replace(".0", ""));

            //create order
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setUserId(RequestBody.getUserId());
            paymentRequest.setTotal(price);
            paymentRequest.setTierId(subscription.getId());
            paymentRequest.setMethod("PayOs");
            Payment payment = paymentService.create(paymentRequest);

            // Gen order code
            String currentTimeString = String.valueOf(String.valueOf(new Date().getTime()));
            Long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));
            paymentService.UpdateRefId(payment.getId(), orderCode);

            returnUrl = returnUrl + orderCode;
            cancelUrl = returnUrl;
            ItemData item = ItemData.builder().name(subscription.getPlanType()).price(price).quantity(1).build();

            PaymentData paymentData = PaymentData.builder().orderCode(orderCode).description(description).amount(price)
                    .item(item).returnUrl(returnUrl).cancelUrl(cancelUrl).build();

            CheckoutResponseData data = payOS.createPaymentLink(paymentData);

            response.put("error", 0);
            response.put("message", "success");
            response.set("data", objectMapper.valueToTree(data));
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", -1);
            response.put("message", "fail");
            response.set("data", null);
            return response;

        }
    }

    @Override
    public ObjectNode getOrderById(long orderId) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();

        try {
            PaymentLinkData order = null;
            order = payOS.getPaymentLinkInformation(orderId);

            response.set("data", objectMapper.valueToTree(order));
            response.put("error", 0);
            response.put("message", "ok");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", -1);
            response.put("message", e.getMessage());
            response.set("data", null);
            return response;
        }

    }

    @Override
    public ObjectNode cancelOrder(int orderId) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            PaymentLinkData order = payOS.cancelPaymentLink(orderId, null);
            response.set("data", objectMapper.valueToTree(order));
            response.put("error", 0);
            response.put("message", "ok");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", -1);
            response.put("message", e.getMessage());
            response.set("data", null);
            return response;
        }
    }

    @Override
    public ObjectNode confirmWebhook(Map<String, String> requestBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            String str = payOS.confirmWebhook(requestBody.get("webhookUrl"));
            response.set("data", objectMapper.valueToTree(str));
            response.put("error", 0);
            response.put("message", "ok");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", -1);
            response.put("message", e.getMessage());
            response.set("data", null);
            return response;
        }
    }

    @Override
    public RedirectView handleOrderStatus(long orderId) {
        try {
            PaymentLinkData order = null;
            order = payOS.getPaymentLinkInformation(orderId);
            Payment payment = paymentService.findByRefId(orderId);
            if (order.getStatus().equals(ConstStatus.TransactionStatus.PAID_STATUS) && payment.getStatus().equals(ConstStatus.TransactionStatus.PAID_STATUS)) {
                return new RedirectView(frontEndHost+"/payment/success");
            }
            if (order.getStatus().equals(ConstStatus.TransactionStatus.PAID_STATUS) && !payment.getStatus().equals(ConstStatus.TransactionStatus.PAID_STATUS)) {
                paymentService.changeStatus(ConstStatus.TransactionStatus.PAID_STATUS, payment.getId());
                Subscription subscription = subscriptionService.findByPlanType(payment.getTierName());
                userService.updateReviewCVTimes(payment.getUser().getId(), subscription.getId(), null);
            } else {
                paymentService.changeStatus(ConstStatus.TransactionStatus.CANCEL_STATUS, payment.getId());
                return new RedirectView(frontEndHost + "/payment/failed");
            }
            return new RedirectView(frontEndHost+"/payment/success");
        } catch (Exception e) {
            e.printStackTrace();
            return new RedirectView(frontEndHost + "/payment/failed");
        }
    }
}
