package com.infor.sra.common.service;

import com.infor.sra.common.model.OrderDTO;

public interface DebitPaymentForOrderService {
  void debitPayment(OrderDTO orderDTO);
}
