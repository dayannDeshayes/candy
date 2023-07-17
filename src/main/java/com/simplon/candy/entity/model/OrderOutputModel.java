package com.simplon.candy.model.model;

import com.simplon.candy.model.enums.CandytagEnum;
import com.simplon.candy.model.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderOutputModel {

    private int utilisateurId;

    private int qte;

    private CandytagEnum candytag;

    private String message;

    private StatusEnum status;
}
