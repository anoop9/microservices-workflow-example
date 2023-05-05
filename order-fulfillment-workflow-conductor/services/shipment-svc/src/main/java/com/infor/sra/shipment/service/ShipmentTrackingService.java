package com.infor.sra.shipment.service;

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
        return uuid.toString();
    }
}
