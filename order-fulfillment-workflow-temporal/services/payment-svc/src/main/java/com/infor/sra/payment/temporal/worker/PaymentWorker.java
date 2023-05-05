package com.infor.sra.payment.temporal.worker;

import com.infor.sra.common.TaskQueue;
import com.infor.sra.common.service.DebitPaymentForOrderService;
import com.infor.sra.payment.temporal.orchestrator.WorkflowOrchestratorClient;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkerOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
public class PaymentWorker {

    @Autowired
    private DebitPaymentForOrderService debitPaymentActivity;
    @Autowired
    private WorkflowOrchestratorClient workflowOrchestratorClient;

    @PostConstruct
    public void createWorker() {

        log.info("Registering Payment worker..");

        var workflowClient = workflowOrchestratorClient.getWorkflowClient();

        var workerOptions = WorkerOptions.newBuilder().build();

        var workerFactory = WorkerFactory.newInstance(workflowClient);
        var worker =
                workerFactory.newWorker(TaskQueue.PAYMENT_ACTIVITY_TASK_QUEUE.name(), workerOptions);

        worker.registerActivitiesImplementations(debitPaymentActivity);

        workerFactory.start();

        log.info("Registered Payment worker..");
    }
}

