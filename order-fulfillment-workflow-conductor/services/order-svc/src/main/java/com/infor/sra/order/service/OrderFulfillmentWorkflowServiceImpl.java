package com.infor.sra.order.service;

import com.infor.sra.common.model.OrderDTO;
import com.infor.sra.common.service.CompleteOrderService;
import com.netflix.conductor.client.http.WorkflowClient;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.common.run.Workflow;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderFulfillmentWorkflowServiceImpl implements OrderFulfillmentWorkflowService {

    private final WorkflowClient workflowClient;
    private final CompleteOrderService completeOrderService;


    @Override
    public void createOrder(OrderDTO orderDTO) {
        log.info("creating order with order id {}", orderDTO.getOrderId());
        StartWorkflowRequest request = new StartWorkflowRequest();
        request.setName("order_fullfillment_workflow"); //name of conductor workflow - same as in workflow-curl file in root folder
        request.setVersion(1);  //version of conductor workflow
        Map<String, Object> inputData = new HashMap<>();
        inputData.put("orderId", orderDTO.getOrderId());
        inputData.put("productId", orderDTO.getProductId());
        inputData.put("price", orderDTO.getPrice());
        inputData.put("quantity", orderDTO.getQuantity());
        request.setInput(inputData);
        log.info("starting workflow : {}", inputData);
        String workflowId = workflowClient.startWorkflow(request);

       log.info("Workflow id: {}", workflowId);
      /*   Workflow workflow =workflowClient.getWorkflow(workflowId, false);
        //takes some time to run, so immediate querying give false
        log.info("Workflow status isSuccessful: {}", workflow.getStatus().isSuccessful()); e*/
        log.info("Completing order..");
        completeOrderService.completeOrder(orderDTO);
    }

}
