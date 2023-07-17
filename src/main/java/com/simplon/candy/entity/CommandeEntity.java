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
@Table(name = "commande", schema = "candy", catalog = "")
public class CommandeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "idUtilisateur")
    private Integer idUtilisateur;
    @Basic
    @Column(name = "idCandytag")
    private Integer idCandytag;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandeEntity that = (CommandeEntity) o;
        return id == that.id && Objects.equals(idUtilisateur, that.idUtilisateur) && Objects.equals(idCandytag, that.idCandytag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idUtilisateur, idCandytag);
    }
}
