package com.infor.sra.payment.temporal.orchestrator;

import com.infor.sra.payment.config.ApplicationProperties;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkflowOrchestratorClient {
    @Autowired
    private ApplicationProperties applicationProperties;

    public WorkflowClient getWorkflowClient() {
        var workflowServiceStubsOptions =
                WorkflowServiceStubsOptions.newBuilder()
                        .setTarget(applicationProperties.getTarget())
                        .build();
        var workflowServiceStubs = WorkflowServiceStubs.newServiceStubs(workflowServiceStubsOptions);
        return WorkflowClient.newInstance(workflowServiceStubs);
    }
}
