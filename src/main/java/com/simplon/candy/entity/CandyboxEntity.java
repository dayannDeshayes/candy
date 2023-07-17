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
@Table(name = "candybox", schema = "candy", catalog = "")
public class CandyboxEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "idCommande")
    private Integer idCommande;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CandyboxEntity that = (CandyboxEntity) o;
        return id == that.id && Objects.equals(idCommande, that.idCommande);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idCommande);
    }
}
