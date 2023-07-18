package com.simplon.candy.entity.DTO;

import com.simplon.candy.entity.enums.CandytagEnum;
import lombok.Data;

@Data
public class OrderInputDTO {
    private int utilisateurId;

    private int qte;

    private CandytagEnum candytag;
}
