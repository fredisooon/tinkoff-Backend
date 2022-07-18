package com.back.backend.classes.repo;

import com.back.backend.classes.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Long> {
}
