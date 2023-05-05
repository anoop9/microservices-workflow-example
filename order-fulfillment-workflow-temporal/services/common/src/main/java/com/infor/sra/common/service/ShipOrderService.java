package com.infor.sra.common.service;

import com.infor.sra.common.model.OrderDTO;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface ShipOrderService {
    void shipOrder(OrderDTO orderDTO);
}
