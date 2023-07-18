package com.simplon.candy.repository;

import com.simplon.candy.entity.CommandeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeRepository extends CrudRepository<CommandeEntity, Integer> {

    CommandeEntity findByUtilisateurId(int utilisateurId);
}
