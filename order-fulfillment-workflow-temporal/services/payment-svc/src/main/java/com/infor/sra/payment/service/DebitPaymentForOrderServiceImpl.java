package com.infor.sra.payment.service;

import com.infor.sra.common.service.DebitPaymentForOrderService;
import com.infor.sra.common.model.OrderDTO;
import com.infor.sra.payment.application.domain.model.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DebitPaymentForOrderServiceImpl implements DebitPaymentForOrderService {

    @Autowired
    private ExternalPaymentService externalPaymentService;

    @Autowired
    private PaymentService paymentService;

    @Override
    public void debitPayment(OrderDTO orderDTO) {
        log.info("Processing payment for order {}", orderDTO.getOrderId());
        double amount = orderDTO.getQuantity() * orderDTO.getPrice();
        // Call external Payment service such as Stripe, adyen etc
        var externalPaymentId = externalPaymentService.debit(amount);
        log.info("Payment processed for order id: {} with external payment tracking id : {}", orderDTO.getOrderId(), externalPaymentId);
        // Create domain object
        var payment =
                Payment.builder()
                        .externalId(externalPaymentId)
                        .orderId(orderDTO.getOrderId())
                        .productId(orderDTO.getProductId())
                        .amount(amount)
                        .build();
        paymentService.save(payment);
    }
}
