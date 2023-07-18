package com.simplon.candy.repository;

import com.simplon.candy.entity.CandyboxEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandyboxRepository extends CrudRepository<CandyboxEntity, Integer> {
}
