package com.exe01.backend.service;

import com.exe01.backend.PayOs.CreatePaymentLinkRequestBody;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

public interface IPayOsService {
    public ObjectNode createPaymentLink(CreatePaymentLinkRequestBody RequestBody);
    public ObjectNode getOrderById(long orderId);
    public ObjectNode cancelOrder( int orderId) ;
    public ObjectNode confirmWebhook( Map<String, String> requestBody);
    public RedirectView handleOrderStatus(long orderId);
    }
