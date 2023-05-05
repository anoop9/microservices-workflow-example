package com.infor.sra.order.service;

import com.infor.sra.common.model.OrderDTO;
import com.infor.sra.order.application.domain.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkflowOrchestratorServiceImpl implements WorkflowOrchestratorService {


    @Autowired
    private OrderFulfillmentWorkflowService orderFulfillmentWorkflowService;

    @Override
    public void createOrder(Order order) {

        var orderDTO = map(order);
        // Execute Sync
        orderFulfillmentWorkflowService.createOrder(orderDTO);
    }

    private OrderDTO map(Order order) {
        var orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setProductId(order.getProductId());
        orderDTO.setPrice(order.getPrice());
        orderDTO.setQuantity(order.getQuantity());
        return orderDTO;
    }
}