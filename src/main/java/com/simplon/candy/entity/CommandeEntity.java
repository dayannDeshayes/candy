package com.simplon.candy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "commande", schema = "candy", catalog = "")
public class CommandeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "idUtilisateur")
    private UtilisateurEntity utilisateur;

    @ManyToOne
    @JoinColumn(name = "idCandytag")
    private CandytagEntity candytag;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<CandyboxEntity> candyboxes = new ArrayList<>();
}
