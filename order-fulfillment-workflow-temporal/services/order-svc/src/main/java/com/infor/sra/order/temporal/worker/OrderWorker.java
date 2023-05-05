package com.infor.sra.order.temporal.worker;

import com.infor.sra.common.TaskQueue;
import com.infor.sra.common.service.CompleteOrderService;
import com.infor.sra.order.service.OrderFulfillmentWorkflowServiceImpl;
import com.infor.sra.order.temporal.orchestrator.WorkflowOrchestratorClient;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkerOptions;
import io.temporal.worker.WorkflowImplementationOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

@Slf4j
@RequiredArgsConstructor
public class OrderWorker {

    private final CompleteOrderService completeOrderActivity;
    private final WorkflowOrchestratorClient workflowOrchestratorClient;

    @PostConstruct
    public void createWorker() {

        log.info("Registering worker..");

        var workerOptions = WorkerOptions.newBuilder().build();

        var workflowClient = workflowOrchestratorClient.getWorkflowClient();
        WorkflowImplementationOptions workflowImplementationOptions =
                WorkflowImplementationOptions.newBuilder()
                        .setFailWorkflowExceptionTypes(NullPointerException.class)
                        .build();

        var workerFactory = WorkerFactory.newInstance(workflowClient);
        var worker =
                workerFactory.newWorker(
                        TaskQueue.ORDER_FULFILLMENT_WORKFLOW_TASK_QUEUE.name(), workerOptions);

        // Can be called multiple times
        worker.registerWorkflowImplementationTypes(
                workflowImplementationOptions, OrderFulfillmentWorkflowServiceImpl.class);

        worker.registerActivitiesImplementations(completeOrderActivity);

        workerFactory.start();

        log.info("Registered order worker..");
    }
}
