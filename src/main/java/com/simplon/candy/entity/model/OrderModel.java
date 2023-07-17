package com.simplon.candy.model.model;

import com.simplon.candy.model.enums.CandytagEnum;
import lombok.Data;

@Data
public class OrderModel {
    private int utilisateurId;

    private int qte;

    private CandytagEnum candytag;
}
