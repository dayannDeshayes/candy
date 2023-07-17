package com.simplon.candy.service;

import com.simplon.candy.entity.model.OrderModel;
import com.simplon.candy.entity.model.OrderOutputModel;

public interface ICandyOrderService {

    public OrderOutputModel processOrder(OrderModel orderModel);
}
