package com.back.backend.rest;


import com.back.backend.classes.Room;
import com.back.backend.exceptions.OptionalNotFoundException;
import com.back.backend.rest.dto.RoomDTO;
import com.back.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomRestController {

    @Autowired
    private RoomService roomService;


    @PostMapping()
    public RoomDTO createNewRoom(@RequestBody String name) {
        Room room = roomService.createRoom(name);
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setName(room.getName());
        roomDTO.setMaxCount(room.getMaxCount());
        roomDTO.setCount(room.getCount());
        roomDTO.setGameId(room.getGame().getId());
        return roomDTO;
    }


    @GetMapping("{id}")
    public RoomDTO roomById(@PathVariable Integer id) throws OptionalNotFoundException {
        Room room = roomService.roomById(id);

        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setName(room.getName());
        roomDTO.setCount(room.getCount());
        roomDTO.setGameId(room.getGame().getId());
        roomDTO.setMaxCount(room.getMaxCount());

        return roomDTO;
    }

    @GetMapping()
    public List<RoomDTO> roomByName(@RequestParam(value = "search") String searchName) {
        List<Room> roomList = roomService.findByNameContaining(searchName);
        List<RoomDTO> roomDTOList = new ArrayList<>();
        for (Room room : roomList) {
            RoomDTO tmpRoomDTO = new RoomDTO();
            tmpRoomDTO.setId(room.getId());
            tmpRoomDTO.setName(room.getName());
            tmpRoomDTO.setCount(room.getCount());
            tmpRoomDTO.setMaxCount(room.getMaxCount());
            tmpRoomDTO.setGameId(room.getGame().getId());
            roomDTOList.add(tmpRoomDTO);
        }
        return roomDTOList;
    }
}
