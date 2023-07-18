package com.simplon.candy.repository;

import com.simplon.candy.entity.ItemcandyboxEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemCandyBoxRepository extends CrudRepository<ItemcandyboxEntity, Integer>  {

    List<ItemcandyboxEntity> findByCandyboxId(int candyboxId);
}
