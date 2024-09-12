package com.exe01.backend.dto.request.transaction;

import com.exe01.backend.constant.ConstStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class PaymentRequest {
    private UUID userId;
    private Double total;
    private UUID tierId;
    private String method;
    private  String status = ConstStatus.PENDING;

    public String vnp_OrderInfo = "Subscription";
    public String vnp_OrderType = "200000";
    public String vnp_TxnRef;
}
