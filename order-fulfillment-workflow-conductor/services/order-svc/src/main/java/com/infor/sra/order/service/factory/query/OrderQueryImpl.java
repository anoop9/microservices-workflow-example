package com.infor.sra.order.service.factory.query;

import com.infor.sra.order.application.domain.model.Order;
import com.infor.sra.order.service.factory.query.OrderQuery;
import com.infor.sra.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderQueryImpl implements OrderQuery {
    @Autowired
    private OrderService orderService;

    @Override
    public Order getOrder(Long orderId) {
        log.info("Fetching Order for id {}", orderId);
        return orderService.get(orderId);
    }
}
