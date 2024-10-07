package com.exe01.backend.dto.response.payment;

import com.exe01.backend.repository.customEntityResponse.PaymentCustomResponse;
import lombok.Data;

import java.util.List;

@Data
public class PaymentDashBoardResponse {
    private List<PaymentCustomResponse> paymentDashBoard;
    private double totalAllPayment;
}
