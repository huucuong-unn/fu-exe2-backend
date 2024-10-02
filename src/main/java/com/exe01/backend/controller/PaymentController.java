package com.exe01.backend.controller;

import com.exe01.backend.constant.ConstAPI;
import com.exe01.backend.dto.response.payment.PaymentResponse;
import com.exe01.backend.exception.BaseException;
import com.exe01.backend.service.IPaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;
import vn.payos.type.Webhook;
import vn.payos.type.WebhookData;

import java.util.List;

@RestController
@CrossOrigin
@Tag(name = "Payment Controller")
@Slf4j
public class PaymentController {
  private final PayOS payOS;

  public PaymentController(PayOS payOS) {
    super();
    this.payOS = payOS;

  }

  @Autowired
  private IPaymentService paymentService;

  @PostMapping(path = "/payment/payos_transfer_handler")
  public ObjectNode payosTransferHandler(@RequestBody ObjectNode body)
      throws JsonProcessingException, IllegalArgumentException {

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode response = objectMapper.createObjectNode();
    Webhook webhookBody = objectMapper.treeToValue(body, Webhook.class);

    try {
      // Init Response
      response.put("error", 0);
      response.put("message", "Webhook delivered");
      response.set("data", null);

      WebhookData data = payOS.verifyPaymentWebhookData(webhookBody);
      System.out.println(data);
      return response;
    } catch (Exception e) {
      e.printStackTrace();
      response.put("error", -1);
      response.put("message", e.getMessage());
      response.set("data", null);
      return response;
    }
  }

  @GetMapping(ConstAPI.PaymentAPI.GET_PAYMENT)
  public List<PaymentResponse> getPayments() throws BaseException{
    log.info("Get all payments");
    return paymentService.getPayments();
  }
}
