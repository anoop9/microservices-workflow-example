package com.infor.sra.order.service;

import com.infor.sra.common.TaskQueue;
import com.infor.sra.common.service.CompleteOrderService;
import com.infor.sra.common.model.OrderDTO;
import com.infor.sra.common.service.DebitPaymentForOrderService;
import com.infor.sra.common.service.ShipOrderService;
import com.infor.sra.order.temporal.workflow.OrderFulfillmentWorkflowService;
import io.temporal.activity.ActivityOptions;
import io.temporal.activity.LocalActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class OrderFulfillmentWorkflowServiceImpl implements OrderFulfillmentWorkflowService {

    private final ActivityOptions shippingActivityOptions =
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofMinutes(1))
                    .setTaskQueue(TaskQueue.SHIPPING_ACTIVITY_TASK_QUEUE.name())
                    .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(3).build())
                    .build();

    private final ActivityOptions paymentActivityOptions =
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofMinutes(1))
                    .setTaskQueue(TaskQueue.PAYMENT_ACTIVITY_TASK_QUEUE.name())
                    .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(3).build())
                    .build();

    private final LocalActivityOptions localActivityOptions =
            LocalActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofMinutes(1))
                    .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(10).build())
                    .build();

    private final CompleteOrderService orderActivity =
            Workflow.newLocalActivityStub(CompleteOrderService.class, localActivityOptions);

    private final DebitPaymentForOrderService paymentActivity =
            Workflow.newActivityStub(DebitPaymentForOrderService.class, paymentActivityOptions);

    private final ShipOrderService shipGoodsActivity =
            Workflow.newActivityStub(ShipOrderService.class, shippingActivityOptions);

    @Override
    public void createOrder(OrderDTO orderDTO) {
        log.info("creating order with order id {}", orderDTO.getOrderId());
        log.info("Workflow ID {}", Workflow.getInfo().getWorkflowId());

        log.info("Debiting payment..");
        paymentActivity.debitPayment(orderDTO);

        log.info("Shipping goods..");
        shipGoodsActivity.shipOrder(orderDTO);

        log.info("Completing order..");
        orderActivity.completeOrder(orderDTO);
    }

}
