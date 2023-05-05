package com.infor.sra.shipment.application.domain.model;

import lombok.*;

/** Shipment Domain object */
@Data
@Builder
public class Shipment {
  private Long shipmentId;
  private Long orderId;
  private Long productId;
  private String trackingId;
  private Double quantity;
}
