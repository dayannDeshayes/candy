package com.simplon.candy.entity.DTO;

import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private int idorder;
    private CandyTagDTO candytag;
    private List<CandyBoxDTO> candybox;

}
