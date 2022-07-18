package com.back.backend.service;


import com.back.backend.classes.Room;
import com.back.backend.classes.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> listRoom() {
        return roomRepository.findAll();
    }

    public Room createRoom(String name) {
        Room room = new Room();
        room.setGameId(1);
        room.setName(name);
        room.setMaxCount(2);
        room.setCount(0);
        return roomRepository.save(room);
    }

    public Optional<Room> roomById(int id) {
        Room room = new Room();
        Optional<Room> byId = roomRepository.findById(id);
        return byId;
    }

    public boolean compareRoomName(String roomName, String searchName) {
        String searchNameLowCase = searchName.toLowerCase();
        if (roomName.contains(searchName) || roomName.equalsIgnoreCase(searchName) ||
                roomName.contains(searchNameLowCase) || roomName.equalsIgnoreCase(searchNameLowCase)) {
            return true;
        }
        return false;
    }
    public List<Room> findByNameContaining(String name) {

        return roomRepository.findByNameContainingIgnoreCase(name);
    }

}