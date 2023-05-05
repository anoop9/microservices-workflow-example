package com.infor.sra.payment.resource;

import com.infor.sra.common.model.OrderDTO;
import com.infor.sra.common.service.DebitPaymentForOrderService;
import com.infor.sra.payment.application.domain.model.Payment;
import com.infor.sra.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private DebitPaymentForOrderService debitPaymentForOrderService;

    @GetMapping()
    public ResponseEntity<List<Payment>> listPayments() {
        log.info("Getting all payments..");
        List<Payment> payments = paymentService.getAll();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @PostMapping("/debit")
    public ResponseEntity<?> debitPaymentForOrder(@Valid @RequestBody OrderDTO orderDTO) {
        log.info("Debiting payment for order details: {}", orderDTO);
        debitPaymentForOrderService.debitPayment(orderDTO);
        log.info("Payment debited for order id :" + orderDTO.getOrderId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
