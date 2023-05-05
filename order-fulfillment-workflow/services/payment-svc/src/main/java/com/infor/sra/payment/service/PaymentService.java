package com.infor.sra.payment.service;

import com.infor.sra.payment.persistence.jpa.PaymentJpaRepository;
import com.infor.sra.payment.application.domain.model.Payment;
import com.infor.sra.payment.persistence.mapper.PaymentPersistableMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PaymentService {
    @Autowired
    private PaymentJpaRepository paymentJpaRepository;

    public Payment save(Payment payment) {
        log.info("Saving Payment");
        var paymentPersistable = PaymentPersistableMapper.MAPPER.map(payment);
        paymentPersistable = paymentJpaRepository.save(paymentPersistable);
        log.info("Payment saved, id {}", paymentPersistable.getId());
        return PaymentPersistableMapper.MAPPER.map(paymentPersistable);
    }

    public List<Payment> getAll() {
        log.info("Getting all payments from DB..");
        var paymentPersistables = paymentJpaRepository.findAll();
        return paymentPersistables.stream().map(PaymentPersistableMapper.MAPPER::map).toList();
    }
}
