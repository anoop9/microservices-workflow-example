package com.infor.sra.order.service;

import com.infor.sra.common.service.CompleteOrderService;
import com.infor.sra.common.model.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Service
public class OrderFulfillmentWorkflowServiceImpl implements OrderFulfillmentWorkflowService {

    private static final String PAYMENT_SERVICE = "http://localhost:8083/payments/debit";
    private static final String SHIPMENT_SERVICE = "http://localhost:8084/shipments/ship";

    @Autowired
    private CompleteOrderService completeOrderService;

    @Override
    public void createOrder(OrderDTO orderDTO) {
        log.info("creating order with order id {}", orderDTO.getOrderId());

        try {
            log.info("Debiting payment..");
            debitPaymentForOrder(orderDTO);

            log.info("Shipping goods..");
            shipOrder(orderDTO);
        } catch (Exception e) {
            log.error("Error while calling payment service or shipping service {}", e.getMessage());
            return;
        }

        log.info("Completing order..");
        completeOrderService.completeOrder(orderDTO);
    }

    /**
     * A basic example to demonstrate a synchronous call between order microservice and payment microservice
     *
     * @param orderDTO
     */
    private void debitPaymentForOrder(OrderDTO orderDTO) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI(PAYMENT_SERVICE);
        ResponseEntity<?> result = restTemplate.postForEntity(uri, orderDTO, OrderDTO.class);
        log.info("HTTP status code {} returned, after debiting payment for order id: {} ",
                result.getStatusCode(), orderDTO.getOrderId());
    }

    /**
     * A basic example to demonstrate a synchronous call between order microservice and shipment microservice
     *
     * @param orderDTO
     */
    private void shipOrder(OrderDTO orderDTO) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI(SHIPMENT_SERVICE);
        ResponseEntity<?> result = restTemplate.postForEntity(uri, orderDTO, OrderDTO.class);
        log.info("HTTP status code {} returned, after shipping order id: {} ",
                result.getStatusCode(), orderDTO.getOrderId());
    }
}
