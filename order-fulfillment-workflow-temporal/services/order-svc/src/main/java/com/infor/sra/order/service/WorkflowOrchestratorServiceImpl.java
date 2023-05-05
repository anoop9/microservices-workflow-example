package com.infor.sra.order.service;

import com.infor.sra.common.TaskQueue;
import com.infor.sra.common.model.OrderDTO;
import com.infor.sra.order.application.domain.model.Order;
import com.infor.sra.order.config.ApplicationProperties;
import com.infor.sra.order.temporal.orchestrator.WorkflowOrchestratorClient;
import com.infor.sra.order.temporal.workflow.OrderFulfillmentWorkflowService;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WorkflowOrchestratorServiceImpl implements WorkflowOrchestratorService {

    private final WorkflowOrchestratorClient workflowOrchestratorClient;
    private final ApplicationProperties applicationProperties;
    // @Autowired
    // private OrderFulfillmentWorkflowService orderFulfillmentWorkflowService;

    @Override
    public void createOrder(Order order) {

        var orderDTO = map(order);
        var workflowClient = workflowOrchestratorClient.getWorkflowClient();
        var orderFulfillmentWorkflow =
                workflowClient.newWorkflowStub(
                        OrderFulfillmentWorkflowService.class,
                        WorkflowOptions.newBuilder()
                                .setWorkflowId(applicationProperties.getWorkflowId() + "-" + orderDTO.getOrderId())
                                .setTaskQueue(TaskQueue.ORDER_FULFILLMENT_WORKFLOW_TASK_QUEUE.name())
                                .build());
        // Execute Sync
        // orderFulfillmentWorkflowService.createOrder(orderDTO);
        WorkflowClient.start(orderFulfillmentWorkflow::createOrder, orderDTO);
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