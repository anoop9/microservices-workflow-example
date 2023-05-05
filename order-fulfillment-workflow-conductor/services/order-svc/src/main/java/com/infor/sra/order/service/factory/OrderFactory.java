package com.infor.sra.order.service.factory;

import com.infor.sra.order.service.factory.command.OrderCommand;
import com.infor.sra.order.service.factory.query.OrderQuery;

public interface OrderFactory {

  OrderCommand getOrderCommand();

  OrderQuery getOrderQuery();
}
