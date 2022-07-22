package com.back.backend.service;


import com.back.backend.classes.Game;
import com.back.backend.classes.Room;
import com.back.backend.classes.repo.RoomRepository;
import com.back.backend.utils.RoomMapper;
import com.back.backend.exceptions.OptionalNotFoundException;
import com.back.backend.utils.OptionalWorker;
import com.back.backend.rest.dto.RoomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomMapper roomMapper;


    public List<Room> listRoom() {
        return roomRepository.findAll();
    }

    public RoomDTO createRoom(String name) {
        Room room = new Room();
        room.setGame(new Game());
        room.setName(name);
        room.setMaxCount(2);
        room.setCount(0);
        roomRepository.save(room);
        return roomMapper.mapToDTO(room);
    }

    public Room roomById(long id) throws OptionalNotFoundException {
        Optional<Room> roomOptional = roomRepository.findById(id);

        OptionalWorker.checkOptional(roomOptional);

        return roomOptional.get();
    }

    public RoomDTO roomByIdDTO(long id) throws OptionalNotFoundException {
        return roomMapper.mapToDTO(this.roomById(id));
    }

    public List<RoomDTO> findByNameContaining(String name) {
        return roomMapper.mapToDTOList(roomRepository.findByNameContainingIgnoreCase(name));
    }
}