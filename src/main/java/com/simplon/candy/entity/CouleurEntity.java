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
@Table(name = "couleur", schema = "candy", catalog = "")
public class CouleurEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "couleur")
    private String couleur;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CouleurEntity that = (CouleurEntity) o;
        return id == that.id && Objects.equals(couleur, that.couleur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, couleur);
    }
}
