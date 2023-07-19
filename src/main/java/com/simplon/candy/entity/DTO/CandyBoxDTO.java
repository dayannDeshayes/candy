package com.simplon.candy.entity.DTO;

import com.simplon.candy.entity.CandyboxEntity;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CandyBoxDTO {
    private int idcandybox;
    private List<ItemCandyBoxDTO> itemcandybox;


    public static CandyBoxDTO fromCandyBoxEntity(CandyboxEntity candyboxEntity) {
        CandyBoxDTO dto = new CandyBoxDTO();
        dto.setIdcandybox(candyboxEntity.getId());
        dto.setItemcandybox(candyboxEntity.getItemCandyboxes().stream().map(ItemCandyBoxDTO::fromItemCandyBoxEntity).collect(Collectors.toList()));
        return dto;
    }
}
