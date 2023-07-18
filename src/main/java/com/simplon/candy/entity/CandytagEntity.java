package com.simplon.candy.entity;

import com.simplon.candy.entity.enums.CandytagEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "candytag", schema = "candy", catalog = "")
public class CandytagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "candytag", nullable = false)
    @Enumerated(EnumType.STRING)
    private CandytagEnum candytag;

    @OneToMany(mappedBy = "candytag", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<CommandeEntity> commandes = new ArrayList<>();
}
