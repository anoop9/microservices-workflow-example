package com.infor.sra.payment.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * To call and external payment provider like Stripe and get an external payment ID back
 */
@Service
public class ExternalPaymentService {

    public String debit(Double amount) {
        UUID uuid = UUID.randomUUID(); //external payment id
        return uuid.toString();
    }
}
