package com.simplon.candy.service;

import com.simplon.candy.entity.*;
import com.simplon.candy.entity.enums.StatusEnum;
import com.simplon.candy.entity.model.OrderModel;
import com.simplon.candy.entity.model.OrderOutputModel;
import com.simplon.candy.service.IService.IProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandyOrderService {

    private final Processor process;
    public OrderOutputModel processOrder(OrderModel orderModel) throws Exception {
        final OrderOutputModel orderOutputModel = new OrderOutputModel();

        IProcessor service = this.process.getProcessService(orderModel.getCandytag());

        orderOutputModel.setCommande(service.filterOrder(orderModel));

        if (orderOutputModel.getMessage() != null) {
            orderOutputModel.setStatus(StatusEnum.ERREUR);
            return orderOutputModel;
        }

        orderOutputModel.setStatus(StatusEnum.OK);

        return orderOutputModel;
    }


    private CommandeEntity asagaoAlgo(OrderModel orderModel) {
        return new CommandeEntity();
    }

    private CommandeEntity kikuAlgo(OrderModel orderModel) {
        return new CommandeEntity();
    }


}
