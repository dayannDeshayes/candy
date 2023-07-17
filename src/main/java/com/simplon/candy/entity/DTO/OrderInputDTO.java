package com.simplon.candy.model.DTO;

import com.simplon.candy.model.enums.CandytagEnum;
import lombok.Data;

@Data
public class OrderInputDTO {
    private int utilisateurId;

    private int qte;

    private CandytagEnum candytag;
}
