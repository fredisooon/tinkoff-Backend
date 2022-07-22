package com.back.backend.service;

import com.back.backend.classes.Player;
import com.back.backend.classes.repo.PlayerRepository;
import com.back.backend.rest.dto.PlayerDTO;
import com.back.backend.utils.PlayerMapper;
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
    @Autowired
    private PlayerMapper playerMapper;


    public List<PlayerDTO> listPlayer() {
        List<Player> playerList = playerRepository.findAll();
        return playerMapper.mapToDTOList(playerList);
    }

    public PlayerDTO create(String name) {
        Player player = new Player();

        player.setName(name);
        playerRepository.save(player);

        return playerMapper.mapToDTO(player);
    }

    public PlayerDTO getPersonDTO(long id) {
        Optional<Player> playerById = playerRepository.findById(id);
        return playerMapper.mapToDTO(playerById.get());
    }

    public Player getPerson(long id) throws OptionalNotFoundException {
        Optional<Player> playerOptional = playerRepository.findById(id);

        OptionalWorker.checkOptional(playerOptional);

        return playerOptional.get();
    }

}
