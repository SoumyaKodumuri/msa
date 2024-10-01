package com.paymentservice.paymentgateway.service;

import com.paymentservice.paymentgateway.dtos.PaymentLinkRequestDto;
import com.paymentservice.paymentgateway.models.PaymentStatus;
import org.springframework.stereotype.Component;

@Component
public interface PaymentGateway {
    String createPaymentLink(PaymentLinkRequestDto paymentLinkRequestDto);
    PaymentStatus getStatus(String paymentId, String orderId);
}
