package com.back.backend.service;

import com.back.backend.classes.Player;
import com.back.backend.classes.Room;
import com.back.backend.classes.repo.PlayerRepository;
import com.back.backend.classes.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private RoomRepository roomRepository;

    public List<Player> listPlayer() {
        return playerRepository.findAll();
    }

    public List<Room> listRoom() {
        return roomRepository.findAll();
    }


    public Player create(String name) {
        Player player = new Player();
        player.setName(name);
        return playerRepository.save(player);
    }

    public Room createRoom(String name) {
        Room room = new Room();
        room.setGameId(1);
        room.setName(name);
        room.setMaxCount(2);
        room.setCount(0);
        return roomRepository.save(room);
    }

    public Optional<Player> getPerson(int id) {
        Player player = new Player();
        Optional<Player> byId = playerRepository.findById(id);
        return byId;
    }

    public  Optional<Room> roomById(int id) {
        Room room = new Room();
        Optional<Room> byId = roomRepository.findById(id);
        return byId;
    }


    public boolean compareName(String roomName, String searchName) {
        String searchNameLowCase = searchName.toLowerCase();
        if (roomName.contains(searchName) || roomName.equalsIgnoreCase(searchName) ||
            roomName.contains(searchNameLowCase) || roomName.equalsIgnoreCase(searchNameLowCase)) {
            return true;
        }
        return false;
    }
}
