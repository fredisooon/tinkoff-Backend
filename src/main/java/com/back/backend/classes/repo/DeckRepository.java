package com.back.backend.classes.repo;

import com.back.backend.classes.Deck;
import org.springframework.data.repository.CrudRepository;

public interface DeckRepository extends CrudRepository<Deck, Long> {
}
