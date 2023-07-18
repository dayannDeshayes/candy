package com.simplon.candy.controller;

import com.simplon.candy.mapper.Mapper;
import com.simplon.candy.entity.DTO.OrderInputDTO;
import com.simplon.candy.entity.DTO.OrderOutputDto;
import com.simplon.candy.service.IService.ICandyOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/candy/order")
public class OrderController {

    private final ICandyOrderService candyOrderService;

    private final Mapper mapper;
    @PostMapping("")
    public void createOrder(@RequestBody OrderInputDTO orderInputDTO) {
       /* OrderOutputDto order = this.mapper.toDto(
                candyOrderService.processOrder(this.mapper.toModel(orderInputDTO))
        );*/
       // return new ResponseEntity<>(order, HttpStatus.valueOf(order.getStatus().ordinal()));
    }
}
