package com.infor.sra.shipment.service;

import com.infor.sra.shipment.application.domain.model.Shipment;
import com.infor.sra.shipment.persistence.mapper.ShipmentPersistableMapper;
import com.infor.sra.shipment.persistence.jpa.ShipmentJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ShipmentService {
    @Autowired
    private ShipmentJpaRepository shipmentJpaRepository;

    public Shipment save(Shipment shipment) {
        log.info("Saving Shipment");
        var shipmentPersistable = ShipmentPersistableMapper.MAPPER.map(shipment);
        shipmentPersistable = shipmentJpaRepository.save(shipmentPersistable);
        log.info("Shipment saved, id {}", shipmentPersistable.getId());
        return ShipmentPersistableMapper.MAPPER.map(shipmentPersistable);
    }

    public List<Shipment> getAll() {
        log.info("Getting all shipments from DB..");
        var shipmentPersistables = shipmentJpaRepository.findAll();
        return shipmentPersistables.stream().map(ShipmentPersistableMapper.MAPPER::map).toList();
    }
}
