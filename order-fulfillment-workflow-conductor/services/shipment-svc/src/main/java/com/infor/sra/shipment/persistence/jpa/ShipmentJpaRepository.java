package com.infor.sra.shipment.persistence.jpa;

import com.infor.sra.shipment.persistence.model.ShipmentPersistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentJpaRepository extends JpaRepository<ShipmentPersistable, Long> {
}
