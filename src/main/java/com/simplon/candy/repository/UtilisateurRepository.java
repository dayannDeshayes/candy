package com.simplon.candy.repository;

import com.simplon.candy.entity.UtilisateurEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends CrudRepository<UtilisateurEntity, Integer> {
}
