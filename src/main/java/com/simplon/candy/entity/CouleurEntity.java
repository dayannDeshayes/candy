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
@Table(name = "couleur", schema = "candy", catalog = "")
public class CouleurEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "couleur", nullable = false)
    private String couleur;

    @OneToMany(mappedBy = "couleur", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ItemcandyboxEntity> itemCandyboxes = new ArrayList<>();
}
