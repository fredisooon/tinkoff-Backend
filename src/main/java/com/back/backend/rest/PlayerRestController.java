package com.back.backend.rest;


import com.back.backend.classes.Player;
import com.back.backend.classes.Room;
import com.back.backend.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class PlayerRestController {

    @Autowired
    private GameService gameService;

    // возвращает массив юзеров из БД
    @GetMapping("/list")
    public List<Player> list() {
        List<Player> playerList = gameService.list();
        return playerList;
    }


    // создаёт нового юзера по имени и присваивает ID
    @GetMapping("/user")  // вернуть на PostMapping после тестов
    public PlayerDTO create(@RequestParam(value = "name") String name) {
        Player player = gameService.create(name);
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setName(player.getName());
        playerDTO.setId(player.getId());
        return playerDTO;
    }

    // нужно ли описывать Entity для Optional<Player>, чтобы добавить туда getId getName для присвоения значений DTO?
    @GetMapping("/getuser")
    public Optional<Player> getperson(@RequestParam(value = "id") Integer id) {
        //PlayerDTO playerDTO = new PlayerDTO();
        //playerDTO.setId(player.get)
        return gameService.getPerson(id);
    }

    // создаёт новую комнату maxCount = 2; count = 0
    @GetMapping("/newroom")
    public RoomDTO createNewRoom(@RequestParam(value = "name") String name) {
        Room room = gameService.createRoom(name);
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setName(room.getName());
        roomDTO.setMaxCount(room.getMaxCount());
        return roomDTO;
    }


    // возвращает комнату по id
    @GetMapping("/roomid")
    public Optional<Room> roomById(@RequestParam(value = "id") Integer id) {
        return gameService.roomById(id);
    }
}
