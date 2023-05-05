package com.infor.sra.shipment.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Mock implementation to represent call to external service to initiate shipping
 * by supplying product id and quantity and return a tracking ID
 */
@Slf4j
@Service
public class ShipmentTrackingService {

    public String shipGoods(Double quantity, Long productId) {
        UUID uuid = UUID.randomUUID();
        try {
            Thread.sleep(12000); //to simulate some delay in generating tracking id for resembling real world external call
        } catch (Exception e) {
            log.info("Error {}", e.getMessage());
        }

        return uuid.toString();
    }
}
