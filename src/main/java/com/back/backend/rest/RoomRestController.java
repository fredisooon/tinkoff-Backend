package com.back.backend.rest;


import com.back.backend.classes.Player;
import com.back.backend.classes.Room;
import com.back.backend.rest.dto.CreateRoomDTO;
import com.back.backend.rest.dto.PlayerDTO;
import com.back.backend.rest.dto.RoomDTO;
import com.back.backend.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomRestController {

    @Autowired
    private GameService gameService;


    @PostMapping("/create")
    public RoomDTO createNewRoom(@RequestBody String name) {
        Room room = gameService.createRoom(name);
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setName(room.getName());
        roomDTO.setMaxCount(room.getMaxCount());
        roomDTO.setCount(room.getCount());
        roomDTO.setGameId(room.getGameId());
        return roomDTO;
    }


    @GetMapping("{id}")
    public RoomDTO roomById(@PathVariable Integer id) {
        Optional<Room> optionalRoom = gameService.roomById(id);
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(optionalRoom.get().getId());
        roomDTO.setName(optionalRoom.get().getName());
        roomDTO.setCount(optionalRoom.get().getCount());
        roomDTO.setGameId(optionalRoom.get().getGameId());
        roomDTO.setMaxCount(optionalRoom.get().getMaxCount());

        return roomDTO;
    }

    @GetMapping("/find")
    public List<RoomDTO> roomByName(@RequestParam(value = "search") String searchName) {
        List<Room> roomList = gameService.listRoom();
        List<RoomDTO> roomDTOList = new ArrayList<>();
        for (Room room : roomList) {
            if (gameService.compareName(room.getName(), searchName)) {
                RoomDTO tmpRoomDTO = new RoomDTO();
                tmpRoomDTO.setId(room.getId());
                tmpRoomDTO.setName(room.getName());
                tmpRoomDTO.setCount(room.getCount());
                tmpRoomDTO.setMaxCount(room.getMaxCount());
                tmpRoomDTO.setGameId(room.getGameId());
                roomDTOList.add(tmpRoomDTO);
            }
        }
        return roomDTOList;
    }
}
