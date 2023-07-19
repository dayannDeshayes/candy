package com.simplon.candy.entity.DTO;

import com.simplon.candy.entity.CouleurEntity;
import lombok.Data;

@Data
public class ColorDTO {
    private int idcolor;
    private String color;

    public static ColorDTO fromColorEntity(CouleurEntity couleurEntity) {
        ColorDTO dto = new ColorDTO();
        dto.setIdcolor(couleurEntity.getId());
        dto.setColor(couleurEntity.getCouleur());
        return dto;
    }

}
