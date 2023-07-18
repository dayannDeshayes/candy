package com.simplon.candy.repository;

import com.simplon.candy.entity.CouleurEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouleurRepository extends CrudRepository<CouleurEntity, Integer> {

    boolean existsByCouleur(String couleur);

    CouleurEntity findByCouleur(String couleur);
}
