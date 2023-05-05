package com.infor.sra.shipment.resource;

import com.infor.sra.common.service.ShipOrderService;
import com.infor.sra.common.model.OrderDTO;
import com.infor.sra.shipment.application.domain.model.Shipment;
import com.infor.sra.shipment.service.ShipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/shipments")
public class ShipmentController {
    @Autowired
    private ShipmentService shipmentService;
    @Autowired
    private ShipOrderService shipOrder;

    @GetMapping()
    public ResponseEntity<List<Shipment>> listShipment() {
        log.info("Getting all shipments..");
        List<Shipment> inventories = shipmentService.getAll();
        return new ResponseEntity<>(inventories, HttpStatus.OK);
    }

    @PostMapping("/ship")
    public ResponseEntity<?> shipOrder(@Valid @RequestBody OrderDTO orderDTO) {
        log.info("Shipping goods with order details: {}", orderDTO);
        shipOrder.shipOrder(orderDTO);
        log.info("Shipped good with order id :" + orderDTO.getOrderId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
