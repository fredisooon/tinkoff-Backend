package com.back.backend.utils;

import com.back.backend.classes.Room;
import com.back.backend.rest.dto.RoomDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomMapper {
    public RoomDTO mapToDTO(Room room) {
        RoomDTO roomDTO = new RoomDTO();

        roomDTO.setId(room.getId());
        roomDTO.setName(room.getName());
        roomDTO.setMaxCount(room.getMaxCount());
        roomDTO.setCount(room.getCount());

        if (room.getGame() != null) {
            roomDTO.setGameId(room.getGame().getId());
        }

        return roomDTO;
    }

    public List<RoomDTO> mapToDTOList(List<Room> roomList) {
        List<RoomDTO> roomDTOList = new ArrayList<>();

        for (Room room : roomList) {
            roomDTOList.add(this.mapToDTO(room));
        }

        return roomDTOList;
    }
}
