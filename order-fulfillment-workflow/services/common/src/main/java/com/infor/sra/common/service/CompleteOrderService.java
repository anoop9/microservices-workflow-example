package com.infor.sra.common.service;

import com.infor.sra.common.model.OrderDTO;

public interface CompleteOrderService {
    void completeOrder(OrderDTO order);
}
