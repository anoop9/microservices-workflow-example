package com.infor.sra.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/** Order DTO object */
@Setter
@Getter
@ToString
public class OrderDTO {
  private Long orderId;
  private Long productId;
  private Double price;
  private Double quantity;
}
