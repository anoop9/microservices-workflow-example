package com.infor.sra.payment.persistence.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "payment")
public class PaymentPersistable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long orderId;
    private String productId;
    private Double amount;
    private String externalId;
}
