package com.simplon.candy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "idCandybox")
    private CandyboxEntity candybox;

    @ManyToOne
    @JoinColumn(name = "idCouleur")
    private CouleurEntity couleur;

    @Column(name = "quantite", nullable = false)
    private int quantite;

    public ItemcandyboxEntity(CandyboxEntity candybox, CouleurEntity couleur, int quantite) {
        this.candybox = candybox;
        this.couleur = couleur;
        this.quantite = quantite;
    }
}
