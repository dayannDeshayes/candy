package com.simplon.candy.entity.model;

import com.simplon.candy.entity.enums.CandytagEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel {

    private int utilisateurId;

    private int qte;

    private CandytagEnum candytag;
}
