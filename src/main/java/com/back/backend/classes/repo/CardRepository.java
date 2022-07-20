package com.back.backend.classes.repo;

import com.back.backend.classes.Card;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardRepository extends CrudRepository<Card, Integer> {
    List<Card> findAll();
}
