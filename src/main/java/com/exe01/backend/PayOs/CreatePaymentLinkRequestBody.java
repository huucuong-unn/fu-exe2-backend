package com.exe01.backend.PayOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class CreatePaymentLinkRequestBody {
  private UUID userId;
  private String productName;
  private String description;
  private String returnUrl;
  private int price;
  private String cancelUrl;

}