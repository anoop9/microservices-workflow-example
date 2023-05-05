package com.infor.sra.order.service;

import com.infor.sra.order.application.domain.model.Order;

public interface WorkflowOrchestratorService {
    void createOrder(Order order);
}
