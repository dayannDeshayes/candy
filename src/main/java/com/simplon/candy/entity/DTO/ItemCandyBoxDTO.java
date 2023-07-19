package com.simplon.candy.entity.DTO;

import com.simplon.candy.entity.ItemcandyboxEntity;
import lombok.Data;

@Data
public class ItemCandyBoxDTO {
    private int iditemcandybox;
    private int qte;
    private ColorDTO color;

    // Getters and Setters

    public static ItemCandyBoxDTO fromItemCandyBoxEntity(ItemcandyboxEntity itemcandyboxEntity) {
        ItemCandyBoxDTO dto = new ItemCandyBoxDTO();
        dto.setIditemcandybox(itemcandyboxEntity.getId());
        dto.setQte(itemcandyboxEntity.getQuantite());
        dto.setColor(ColorDTO.fromColorEntity(itemcandyboxEntity.getCouleur()));
        return dto;
    }
}
