package com.back.backend.classes.repo;

import com.back.backend.classes.PlayerDeck;
import com.back.backend.classes.PlayerDeckId;
import org.springframework.data.repository.CrudRepository;

public interface PlayerDeckRepository extends CrudRepository<PlayerDeck, PlayerDeckId> {
}
