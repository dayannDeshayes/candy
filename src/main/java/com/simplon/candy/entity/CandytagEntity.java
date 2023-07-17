package com.simplon.candy.model;

import com.simplon.candy.model.enums.CandytagEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "candytag", schema = "candy", catalog = "")
public class CandytagEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "candytag")
    @Enumerated(EnumType.STRING)
    private CandytagEnum candytag;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CandytagEntity that = (CandytagEntity) o;
        return id == that.id && Objects.equals(candytag, that.candytag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, candytag);
    }
}
