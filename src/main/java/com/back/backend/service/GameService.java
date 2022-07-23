package com.back.backend.service;


import com.back.backend.classes.Game;
import com.back.backend.classes.repo.GameRepository;
import com.back.backend.classes.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public Game createGame() {
        Game game = new Game();
        return gameRepository.save(game);
    }
}
