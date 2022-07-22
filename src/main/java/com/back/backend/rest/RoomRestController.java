package com.back.backend.rest;

import com.back.backend.rest.requestsClasses.CreateRoomRequest;
import com.back.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
public class RoomRestController {

    @Autowired
    private RoomService roomService;

    @PostMapping()
    public ResponseEntity createNewRoom(@RequestBody CreateRoomRequest createRoomRequest) {
        try {
            return ResponseEntity.ok(roomService.createRoom(createRoomRequest.getName()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Не получилось создать новую комнату");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity roomById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(roomService.roomByIdDTO(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Не получилось получить комнату");
        }
    }

    @GetMapping()
    public ResponseEntity roomByName(@RequestParam(value = "search") String searchName) {
        try {
            return ResponseEntity.ok(roomService.findByNameContaining(searchName));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Не получилось получить комнату");
        }
    }
}
