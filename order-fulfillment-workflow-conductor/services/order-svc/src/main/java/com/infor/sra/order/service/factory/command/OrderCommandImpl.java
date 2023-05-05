package com.infor.sra.order.service.factory.command;

import com.infor.sra.order.application.domain.model.Order;
import com.infor.sra.order.application.domain.model.OrderStatus;
import com.infor.sra.order.service.OrderService;
import com.infor.sra.order.service.WorkflowOrchestratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderCommandImpl implements OrderCommand {

    @Autowired
    private OrderService orderService;

    @Autowired
    private WorkflowOrchestratorService workflowOrchestratorService;

    @Override
    public Order createOrder(Order order) {
        log.info("Creating order..");
        order.setOrderStatus(OrderStatus.PENDING);
        var persistedOrder = orderService.save(order);
        workflowOrchestratorService.createOrder(persistedOrder);
        return persistedOrder;
    }
}
