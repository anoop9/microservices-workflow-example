package com.infor.sra.shipment.persistence.mapper;

import com.infor.sra.shipment.application.domain.model.Shipment;
import com.infor.sra.shipment.persistence.model.ShipmentPersistable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ShipmentPersistableMapper {

  public static final ShipmentPersistableMapper MAPPER =
      Mappers.getMapper(ShipmentPersistableMapper.class);

  @Mapping(source = "shipmentId", target = "id")
  public abstract ShipmentPersistable map(Shipment shipment);

  @Mapping(source = "id", target = "shipmentId")
  public abstract Shipment map(ShipmentPersistable shipmentPersistable);
}
