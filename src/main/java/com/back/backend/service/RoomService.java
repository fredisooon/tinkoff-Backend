package com.back.backend.service;

import com.back.backend.classes.Player;
import com.back.backend.classes.Room;
import com.back.backend.classes.repo.PlayerRepository;
import com.back.backend.classes.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public Room createRoom(String name) {
        Room room = new Room();
        room.setName(name);
        room.setMaxCount(2);
        room.setCount(0);
        return roomRepository.save(room);
    }

    public void deleteRoom(Room room){
        roomRepository.delete(room);
    }

    public Room roomById(Long id) {
        Room room = new Room();
        Optional<Room> byId = roomRepository.findById(id);

        return byId.get();
    }

    public Room update(Room newRoom) {
        return roomRepository.save(newRoom);
    }

    public List<Room> findByNameContaining(String name) {

        return roomRepository.findByNameContainingIgnoreCase(name);
    }
}

