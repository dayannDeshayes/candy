package com.simplon.candy.service.IService;

import com.simplon.candy.entity.CommandeEntity;
import com.simplon.candy.entity.model.OrderModel;

public interface IProcessor {
    CommandeEntity filterOrder(OrderModel orderModel) throws Exception;
}
