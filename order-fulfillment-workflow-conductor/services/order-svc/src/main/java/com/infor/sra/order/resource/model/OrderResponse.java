package com.infor.sra.order.resource.model;

import com.infor.sra.order.application.domain.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class OrderResponse {
  private Long orderId;
  private OrderStatus orderStatus;
}
