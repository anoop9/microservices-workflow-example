package com.infor.sra.shipment.temporal.worker;

import com.infor.sra.common.TaskQueue;
import com.infor.sra.common.service.ShipOrderService;
import com.infor.sra.shipment.temporal.orchestrator.WorkflowOrchestratorClient;
import io.temporal.worker.WorkerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
public class ShipmentWorker {

    @Autowired
    private ShipOrderService shipOrderServiceActivity;
    @Autowired
    private WorkflowOrchestratorClient workflowOrchestratorClient;

    @PostConstruct
    public void createWorker() {

        log.info("Registering worker..");

        var workflowClient = workflowOrchestratorClient.getWorkflowClient();

        var workerFactory = WorkerFactory.newInstance(workflowClient);
        var worker = workerFactory.newWorker(TaskQueue.SHIPPING_ACTIVITY_TASK_QUEUE.name());

        worker.registerActivitiesImplementations(shipOrderServiceActivity);

        workerFactory.start();

        log.info("Shipment worker started..");
    }
}
