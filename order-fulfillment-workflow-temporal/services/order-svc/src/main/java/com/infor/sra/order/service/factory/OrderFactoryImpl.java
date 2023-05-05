package com.infor.sra.order.service.factory;

import com.infor.sra.order.service.factory.command.OrderCommand;
import com.infor.sra.order.service.factory.query.OrderQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderFactoryImpl implements OrderFactory {
    @Autowired
    private OrderCommand orderCommand;
    @Autowired
    private OrderQuery orderQuery;


    public OrderCommand getOrderCommand() {
        return orderCommand;
    }

    @Override
    public OrderQuery getOrderQuery() {
        return orderQuery;
    }
}
