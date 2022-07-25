package com.back.backend.rest;

import com.back.backend.classes.Room;
import com.back.backend.rest.dto.RoomDTO;
import com.back.backend.rest.requestsClasses.CreateRoomRequest;
import com.back.backend.service.RoomService;
import com.back.backend.utils.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomRestController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomMapper roomMapper;

    @PostMapping()
    public ResponseEntity createNewRoom(@RequestBody CreateRoomRequest createRoomRequest) {
        try {
            Room room = roomService.createRoom(createRoomRequest.getName());
            RoomDTO roomDTO = roomMapper.mapToDTO(room);

            return ResponseEntity.ok(roomDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Не получилось создать новую комнату");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity roomById(@PathVariable long id) {
        try {
            Room room = roomService.roomById(id);
            RoomDTO roomDTO = roomMapper.mapToDTO(room);

            return ResponseEntity.ok(roomDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Не получилось получить комнату");
        }
    }

    @GetMapping()
    public ResponseEntity roomByName(@RequestParam(value = "search") String searchName) {
        try {
            List<Room> roomList = roomService.findByNameContaining(searchName);
            List<RoomDTO> roomDTOList = roomMapper.mapToDTOList(roomList);

            return ResponseEntity.ok(roomDTOList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Не получилось получить комнату");
        }
    }
}
