package com.simplon.candy.entity.model;

import com.simplon.candy.entity.CommandeEntity;
import com.simplon.candy.entity.enums.CandytagEnum;
import com.simplon.candy.entity.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderOutputModel {

    private CommandeEntity commande;

    private String message = "";

    private StatusEnum status;
}
