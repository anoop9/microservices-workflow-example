package com.infor.sra.order;

import com.netflix.conductor.client.http.WorkflowClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@Slf4j
@SpringBootApplication
public class OrderSvcApplication {

    @Value("${conductor.client.rootURI}")
    private String conductorURI;

    public static void main(String[] args) {
        SpringApplication.run(OrderSvcApplication.class, args);
    }


    @Bean
    public WorkflowClient getWorkflowClient() {
        WorkflowClient workflowClient = new WorkflowClient();
        log.info(" conductor root url {}", conductorURI);
        workflowClient.setRootURI(conductorURI);
        return workflowClient;
    }

}
