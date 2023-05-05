package com.infor.sra.order.service;

import com.infor.sra.common.model.OrderDTO;


public interface OrderFulfillmentWorkflowService {

    void createOrder(OrderDTO orderDTO);
}
