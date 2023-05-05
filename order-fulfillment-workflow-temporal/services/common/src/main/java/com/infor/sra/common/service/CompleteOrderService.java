package com.infor.sra.common.service;

import io.temporal.activity.ActivityInterface;
import com.infor.sra.common.model.OrderDTO;

@ActivityInterface
public interface CompleteOrderService {
    void completeOrder(OrderDTO order);
}
