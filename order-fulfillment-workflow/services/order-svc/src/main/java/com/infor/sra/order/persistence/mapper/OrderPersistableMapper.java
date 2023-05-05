package com.infor.sra.order.persistence.mapper;

import com.infor.sra.order.persistence.repository.model.OrderPersistable;
import com.infor.sra.order.application.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class OrderPersistableMapper {

  public static final OrderPersistableMapper MAPPER =
      Mappers.getMapper(OrderPersistableMapper.class);

  @Mapping(source = "orderId", target = "id")
  public abstract OrderPersistable map(Order order);

  @Mapping(source = "id", target = "orderId")
  public abstract Order map(OrderPersistable orderPersistable);
}
