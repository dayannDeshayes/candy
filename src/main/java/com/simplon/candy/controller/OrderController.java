package com.simplon.candy.controller;

import com.simplon.candy.entity.model.OrderModel;
import com.simplon.candy.mapper.Mapper;
import com.simplon.candy.entity.DTO.OrderInputDTO;
import com.simplon.candy.mapper.ResponseDtoMapper;
import com.simplon.candy.service.CandyOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/candy/order")
public class OrderController {

    private final Mapper mapper;
    private final CandyOrderService candyOrderService;


    @PostMapping("")
    public ResponseDtoMapper createOrder(@RequestBody OrderInputDTO orderInputDTO) {
        try {
            OrderModel orderModel = this.mapper.toModel(orderInputDTO);
            return ResponseDtoMapper.fromOrderOutputModel(this.candyOrderService.processOrder(orderModel));
        } catch (Exception e) {
            ResponseDtoMapper dto = new ResponseDtoMapper();
            dto.setMsgerr(e.getMessage());
            dto.setStatus(500);
            return dto;
        }
    }
}
