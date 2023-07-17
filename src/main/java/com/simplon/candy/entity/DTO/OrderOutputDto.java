package com.simplon.candy.model.DTO;

import com.simplon.candy.model.enums.CandytagEnum;
import com.simplon.candy.model.enums.StatusEnum;
import lombok.Data;

@Data
public class OrderOutputDto {

    private int utilisateurId;

    private int qte;

    private CandytagEnum candytag;

    private String message;

    private StatusEnum status;
}
