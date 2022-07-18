package com.back.backend.service;

import com.back.backend.classes.Game;
import com.back.backend.classes.Player;
import com.back.backend.classes.Room;
import com.back.backend.classes.repo.PlayerRepository;
import com.back.backend.classes.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private RoomRepository roomRepository;

    public List<Player> list() {
        return playerRepository.findAll();
    }

    public Player create(String name) {
        Player player = new Player();
        player.setName(name);
        Player save = playerRepository.save(player);
        return save;
    }

    public Room createRoom(String name) {
        Room room = new Room();
        room.setGame(new Game());
        room.setName(name);
        room.setMaxCount(2);
        room.setCount(0);
        return roomRepository.save(room);
    }

    public Optional<Player> getPerson(long id) {
        Player player = new Player();
        Optional<Player> byId = playerRepository.findById(id);
        return byId;
    }

    public  Optional<Room> roomById(long id) {
        Room room = new Room();
        Optional<Room> byId = roomRepository.findById(id);
        return byId;
    }
}
