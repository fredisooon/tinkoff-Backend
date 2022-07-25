package com.back.backend.service;

import com.back.backend.classes.Game;
import com.back.backend.classes.Player;
import com.back.backend.classes.PlayerDeck;
import com.back.backend.classes.PlayerDeckId;
import com.back.backend.classes.repo.PlayerDeckRepository;
import com.back.backend.exceptions.OptionalNotFoundException;
import com.back.backend.utils.OptionalWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerDeckService {
    @Autowired
    private PlayerDeckRepository playerDeckRepository;

    public PlayerDeck getPlayerDeck(Player player, Game game) throws OptionalNotFoundException {
        PlayerDeckId playerDeckId = new PlayerDeckId();

        playerDeckId.setPlayerId(player.getId());
        playerDeckId.setGameId(game.getId());

        Optional<PlayerDeck> playerDeckOptional = playerDeckRepository.findById(playerDeckId);
        OptionalWorker.checkOptional(playerDeckOptional);

        return playerDeckOptional.get();
    }
}