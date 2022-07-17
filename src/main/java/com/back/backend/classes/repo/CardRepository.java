package com.back.backend.classes.repo;

import com.back.backend.classes.Card;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<Card, Integer> {
}
