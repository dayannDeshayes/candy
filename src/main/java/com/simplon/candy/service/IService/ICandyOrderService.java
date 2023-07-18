package com.simplon.candy.service.IService;

import com.simplon.candy.entity.model.OrderModel;
import com.simplon.candy.entity.model.OrderOutputModel;

public interface ICandyOrderService {

    OrderOutputModel processOrder(OrderModel orderModel);
}
