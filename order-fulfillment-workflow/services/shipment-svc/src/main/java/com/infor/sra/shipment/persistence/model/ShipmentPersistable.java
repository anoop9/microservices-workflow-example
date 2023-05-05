package com.infor.sra.shipment.persistence.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "shipment")
public class ShipmentPersistable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long orderId;
    private String productId;
    private Double quantity;
    private String trackingId;
}
