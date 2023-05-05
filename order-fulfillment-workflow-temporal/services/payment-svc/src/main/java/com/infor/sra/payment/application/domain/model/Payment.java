package com.infor.sra.payment.application.domain.model;

import lombok.*;

/**
 * Payment Domain object
 */
@Data
@Builder
public class Payment {
    private Long paymentId;
    private Long orderId;
    private Long productId;
    private Double amount;
    private String externalId;
}
