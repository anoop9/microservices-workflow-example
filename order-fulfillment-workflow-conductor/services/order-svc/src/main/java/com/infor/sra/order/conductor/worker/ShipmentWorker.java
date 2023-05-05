package com.infor.sra.order.conductor.worker;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ShipmentWorker implements Worker {
    private final String taskDefName = "http_task_ship_order";

    @Override
    public String getTaskDefName() {
        return taskDefName;
    }

    @Override
    public TaskResult execute(Task task) {
        System.out.printf("Executing %s%n", taskDefName);

        TaskResult result = new TaskResult(task);
        result.setStatus(TaskResult.Status.COMPLETED);
        //Register the output of the task
        System.out.printf("Executed task %s%n", taskDefName);
        return result;
    }

}