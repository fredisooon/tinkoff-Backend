package com.back.backend.service;

import com.back.backend.classes.Player;
import com.back.backend.classes.Room;
import com.back.backend.classes.repo.PlayerRepository;
import com.back.backend.classes.repo.RoomRepository;
import com.back.backend.exceptions.OptionalNotFoundException;
import com.back.backend.rest.dto.PlayerDTO;
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
    private RoomService roomService;

    @Autowired
    private RoomRepository roomRepository;


    public Player create(String name) {
        Player player = new Player();

        player.setName(name);
        playerRepository.save(player);

        return player;
    }

    public Player getPerson(long id) throws OptionalNotFoundException {
        Optional<Player> playerOptional = playerRepository.findById(id);

        OptionalWorker.checkOptional(playerOptional);

        return playerOptional.get();
    }

    public Player update(PlayerDTO newPlayer, Room room) {
        Player player = new Player();
        player.setId(newPlayer.getId());
        player.setName(newPlayer.getName());
        player.setRoom(room);

        return playerRepository.save(player);
    }

    public Player updateRoomId(PlayerDTO newPlayer) throws OptionalNotFoundException {
        Player player = this.getPerson(newPlayer.getId());

        if (player.getRoom() == null && newPlayer.getRoomId() != null) {
            Optional<Room> newRoomOptional = roomRepository.findById(newPlayer.getRoomId());
            Room newRoom = newRoomOptional.orElseGet(Room::new);

            newRoom.addPlayer(player);
            roomRepository.save(newRoom);
            playerRepository.save(player);

            roomService.checkRoomPlayersCount(newRoom);
        } else {
            Room oldRoom = player.getRoom();
            oldRoom.removePlayer(player);

            roomRepository.save(oldRoom);
            playerRepository.save(player);

            roomService.checkRoomPlayersCount(oldRoom);
        }

        return player;
    }
}

