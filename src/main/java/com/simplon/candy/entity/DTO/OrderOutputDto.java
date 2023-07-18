package com.simplon.candy.entity.DTO;

import com.simplon.candy.entity.enums.CandytagEnum;
import com.simplon.candy.entity.enums.StatusEnum;
import lombok.Data;

@Data
public class OrderOutputDto {

    private int utilisateurId;

    private int qte;

    private CandytagEnum candytag;

    private String message;

    private StatusEnum status;
}
