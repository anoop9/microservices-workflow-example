package com.infor.sra.shipment.service;

import com.infor.sra.common.service.ShipOrderService;
import com.infor.sra.common.model.OrderDTO;
import com.infor.sra.shipment.application.domain.model.Shipment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShipOrderServiceImpl implements ShipOrderService {
    @Autowired
    private ShipmentTrackingService shipmentTrackingService;
    @Autowired
    private ShipmentService shipmentService;

    @Override
    public void shipOrder(OrderDTO orderDTO) {

        log.info("Dispatching shipment,  order id {}", orderDTO.getOrderId());
        var trackingId = shipmentTrackingService.shipGoods(orderDTO.getQuantity(), orderDTO.getProductId());
        log.info("Shipment with  order id: {} , has external shipment tracking id: {}", orderDTO.getOrderId(), trackingId);
        var shipment =
                Shipment.builder()
                        .orderId(orderDTO.getOrderId())
                        .productId(orderDTO.getProductId())
                        .quantity(orderDTO.getQuantity())
                        .trackingId(trackingId)
                        .build();
        shipmentService.save(shipment);

        log.info("Created shipment for order id {}", orderDTO.getOrderId());
    }
}
