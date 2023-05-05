package com.infor.sra.order.temporal.workflow;

import com.infor.sra.common.model.OrderDTO;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface OrderFulfillmentWorkflowService {
    @WorkflowMethod
    void createOrder(OrderDTO orderDTO);
}
