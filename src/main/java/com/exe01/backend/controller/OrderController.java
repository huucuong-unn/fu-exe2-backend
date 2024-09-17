package com.exe01.backend.controller;

import com.exe01.backend.PayOs.CreatePaymentLinkRequestBody;
import com.exe01.backend.service.IPayOsService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    IPayOsService payOS;

    @PostMapping(path = "/create")
    public ObjectNode createPaymentLink(@RequestBody CreatePaymentLinkRequestBody RequestBody) {
    return payOS.createPaymentLink(RequestBody);
    }

    @GetMapping(path = "/{orderId}")
    public ObjectNode getOrderById(@PathVariable("orderId") long orderId) {
        return payOS.getOrderById(orderId);
    }

    @PutMapping(path = "/{orderId}")
    public ObjectNode cancelOrder(@PathVariable("orderId") int orderId) {
        return payOS.cancelOrder(orderId);
    }

    @PostMapping(path = "/confirm-webhook")
    public ObjectNode confirmWebhook(@RequestBody Map<String, String> requestBody) {
        return payOS.confirmWebhook(requestBody);
    }

    @GetMapping(path = "/handle-order-status/{orderId}")
    public RedirectView handleOrderStatus(@PathVariable("orderId") long orderId) {
        return payOS.handleOrderStatus(orderId);
    }
}
