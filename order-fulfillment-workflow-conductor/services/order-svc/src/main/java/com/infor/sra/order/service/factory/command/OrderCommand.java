package com.infor.sra.order.service.factory.command;

import com.infor.sra.order.application.domain.model.Order;

public interface OrderCommand {
  Order createOrder(Order order);
}
