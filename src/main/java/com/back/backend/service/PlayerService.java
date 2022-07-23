package com.back.backend.service;

import com.back.backend.classes.Player;
import com.back.backend.classes.Room;
import com.back.backend.classes.repo.PlayerRepository;
import com.back.backend.rest.dto.PlayerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private RoomService roomService;

    public List<Player> list() {
        return playerRepository.findAll();
    }

    public Player create(String name) {
        Player player = new Player();
        player.setName(name);
        Player save = playerRepository.save(player);
        return save;
    }

    public Optional<Player> getPlayer(Long id) {
        Player player = new Player();
        Optional<Player> byId = playerRepository.findById(id);
        return byId;
    }

    public Player update(PlayerDTO newPlayer, Room room) {
        Player player = new Player();
        player.setId(newPlayer.getId());
        player.setName(newPlayer.getName());
        player.setRoom(room);

        return playerRepository.save(player);
    }

    public PlayerDTO updateRoomId(PlayerDTO newPlayer){
        Optional<Player> currentPlayer = getPlayer(newPlayer.getId());
        Room room;

        PlayerDTO playerDTO = new PlayerDTO();

        if (newPlayer.getRoomId() != null){
            room = roomService.roomById(newPlayer.getRoomId());
            room.addPlayer(currentPlayer.get());
            roomService.update(room);

            playerDTO.setId(currentPlayer.get().getId());
            playerDTO.setName(currentPlayer.get().getName());
            playerDTO.setRoomId(currentPlayer.get().getRoom().getId());
        }
        else  {
            room = roomService.roomById(currentPlayer.get().getRoom().getId());
            room.removePlayer(currentPlayer.get());
            roomService.update(room);

            playerDTO.setId(currentPlayer.get().getId());
            playerDTO.setName(currentPlayer.get().getName());
            playerDTO.setRoomId(null);
        }

        checkPlayersCount(room);

        return playerDTO;
    }

    public void checkPlayersCount(Room room){
        if (room.getCount() == 0){
            roomService.deleteRoom(room);
        }
         if (room.getCount() == room.getMaxCount()) {
             // Метод по старту игры
         }
    }
}

