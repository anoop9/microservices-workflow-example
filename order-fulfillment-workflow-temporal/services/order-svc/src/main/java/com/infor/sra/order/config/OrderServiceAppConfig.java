package com.infor.sra.order.config;


import com.infor.sra.common.service.CompleteOrderService;
import com.infor.sra.order.service.OrderService;
import com.infor.sra.order.service.WorkflowOrchestratorService;
import com.infor.sra.order.service.WorkflowOrchestratorServiceImpl;
import com.infor.sra.order.service.factory.command.OrderCommand;
import com.infor.sra.order.service.factory.command.OrderCommandImpl;
import com.infor.sra.order.temporal.orchestrator.WorkflowOrchestratorClient;
import com.infor.sra.order.temporal.worker.OrderWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderServiceAppConfig {

    @Bean
    public OrderCommand orderCommand(OrderService orderService, ApplicationProperties applicationProperties) {
        return new OrderCommandImpl(orderService, workflowOrchestrator(applicationProperties));
    }

    @Bean
    public OrderWorker orderWorker(CompleteOrderService completeOrderServiceActivity, ApplicationProperties applicationProperties) {
        return new OrderWorker(
                completeOrderServiceActivity, workflowOrchestratorClient(applicationProperties));
    }

    @Bean
    public WorkflowOrchestratorClient workflowOrchestratorClient(ApplicationProperties applicationProperties) {
        return new WorkflowOrchestratorClient(applicationProperties);
    }

    @Bean
    public WorkflowOrchestratorService workflowOrchestrator(ApplicationProperties applicationProperties) {
        return new WorkflowOrchestratorServiceImpl(workflowOrchestratorClient(applicationProperties), applicationProperties);
    }
}

