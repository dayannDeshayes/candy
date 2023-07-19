package com.simplon.candy.mapper;

import com.simplon.candy.entity.DTO.CandyBoxDTO;
import com.simplon.candy.entity.DTO.CandyTagDTO;
import com.simplon.candy.entity.DTO.OrderDTO;
import com.simplon.candy.entity.model.OrderOutputModel;
import lombok.Data;

import java.util.stream.Collectors;

@Data
public class ResponseDtoMapper {
        private OrderDTO order;
        private String msgerr;
        private int status;

        public static ResponseDtoMapper fromOrderOutputModel(OrderOutputModel orderOutputModel) {
            ResponseDtoMapper dto = new ResponseDtoMapper();

            OrderDTO order = new OrderDTO();
            order.setIdorder(orderOutputModel.getCommande().getId());
            order.setCandytag(CandyTagDTO.fromCandyTagEntity(orderOutputModel.getCommande().getCandytag()));
            order.setCandybox(orderOutputModel.getCommande().getCandyboxes().stream().map(CandyBoxDTO::fromCandyBoxEntity).collect(Collectors.toList()));

            dto.setOrder(order);
            dto.setMsgerr("");
            dto.setStatus(200);

            return dto;
        }
    }
