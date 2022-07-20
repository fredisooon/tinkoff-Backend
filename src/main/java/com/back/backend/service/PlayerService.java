package com.back.backend.service;

import com.back.backend.classes.Player;
import com.back.backend.classes.repo.PlayerRepository;
import com.back.backend.exceptions.OptionalNotFoundException;
import com.back.backend.utils.OptionalWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public List<Player> listPlayer() {
        return playerRepository.findAll();
    }

    public Player create(String name) {
        Player player = new Player();

        player.setName(name);

        return playerRepository.save(player);
    }

    public Player getPlayer(long id) throws OptionalNotFoundException {
        Optional<Player> playerOptional = playerRepository.findById(id);

        OptionalWorker.checkOptional(playerOptional);

        return playerOptional.get();
    }

}
