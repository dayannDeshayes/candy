package com.simplon.candy.controller;

import com.simplon.candy.entity.model.OrderOutputModel;
import com.simplon.candy.mapper.Mapper;
import com.simplon.candy.entity.DTO.OrderInputDTO;
import com.simplon.candy.entity.DTO.OrderOutputDto;
import com.simplon.candy.mapper.ResponseDtoMapper;
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
    public ResponseDtoMapper createOrder(@RequestBody OrderInputDTO orderInputDTO) {
        try {
            return ResponseDtoMapper.fromOrderOutputModel(this.candyOrderService.processOrder(this.mapper.toModel(orderInputDTO)));
        } catch (Exception e) {
            ResponseDtoMapper dto = new ResponseDtoMapper();
            dto.setMsgerr(e.getMessage());
            dto.setStatus(500);
            return dto;
        }
    }
}
