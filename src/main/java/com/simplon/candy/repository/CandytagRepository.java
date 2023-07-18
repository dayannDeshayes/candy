package com.simplon.candy.repository;

import com.simplon.candy.entity.CandytagEntity;
import com.simplon.candy.entity.enums.CandytagEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandytagRepository extends CrudRepository<CandytagEntity, Integer> {

    boolean existsByCandytag(CandytagEnum candytagEnum);

    CandytagEntity findByCandytag(CandytagEnum candytagEnum);
}
