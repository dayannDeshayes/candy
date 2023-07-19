package com.simplon.candy.entity.DTO;

import com.simplon.candy.entity.CandytagEntity;
import lombok.Data;

@Data
public class CandyTagDTO {
    private int idcandytag;
    private String candytag;

    public static CandyTagDTO fromCandyTagEntity(CandytagEntity candytagEntity) {
        CandyTagDTO dto = new CandyTagDTO();
        dto.setIdcandytag(candytagEntity.getId());
        dto.setCandytag(candytagEntity.getCandytag().name());
        return dto;
    }
}
