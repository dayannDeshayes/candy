package com.simplon.candy.entity.DTO;

import com.simplon.candy.entity.CommandeEntity;
import com.simplon.candy.entity.enums.CandytagEnum;
import com.simplon.candy.entity.enums.StatusEnum;
import lombok.Data;

@Data
public class OrderOutputDto {

    private CommandeEntity commande;

    private String message;

    private StatusEnum status;
}
