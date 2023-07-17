package com.simplon.candy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "itemcandybox", schema = "candy", catalog = "")
public class ItemcandyboxEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "idCandybox")
    private Integer idCandybox;
    @Basic
    @Column(name = "idCouleur")
    private Integer idCouleur;
    @Basic
    @Column(name = "quantite")
    private int quantite;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemcandyboxEntity that = (ItemcandyboxEntity) o;
        return id == that.id && quantite == that.quantite && Objects.equals(idCandybox, that.idCandybox) && Objects.equals(idCouleur, that.idCouleur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idCandybox, idCouleur, quantite);
    }
}
