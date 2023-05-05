package com.infor.sra.order.service.factory.query;

import com.infor.sra.order.application.domain.model.Order;

public interface OrderQuery {
  Order getOrder(Long orderId);
}
