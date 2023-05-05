package com.infor.sra.common.service;

import com.infor.sra.common.model.OrderDTO;

public interface ShipOrderService {
  void shipOrder(OrderDTO orderDTO);
}
