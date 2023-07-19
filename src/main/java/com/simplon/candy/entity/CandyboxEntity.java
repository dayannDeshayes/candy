package com.simplon.candy.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "candybox", schema = "candy", catalog = "")
public class CandyboxEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "idCommande")
    private CommandeEntity commande;

    @OneToMany(mappedBy = "candybox", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ItemcandyboxEntity> itemCandyboxes = new ArrayList<>();

    public CandyboxEntity(CommandeEntity commande) {
        this.commande = commande;
    }
}
