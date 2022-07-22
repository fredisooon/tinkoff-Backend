package com.back.backend.rest;


import com.back.backend.exceptions.OptionalNotFoundException;
import com.back.backend.rest.dto.RoomDTO;
import com.back.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomRestController {

    @Autowired
    private RoomService roomService;

    @PostMapping()
    public RoomDTO createNewRoom(@RequestBody String name) {
        return roomService.createRoom(name);
    }

    @GetMapping("{id}")
    public RoomDTO roomById(@PathVariable Integer id) throws OptionalNotFoundException {
        return roomService.roomByIdDTO(id);
    }

    @GetMapping()
    public List<RoomDTO> roomByName(@RequestParam(value = "search") String searchName) {
        return roomService.findByNameContaining(searchName);
    }
}
