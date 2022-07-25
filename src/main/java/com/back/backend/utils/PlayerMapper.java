package com.back.backend.utils;

import com.back.backend.classes.Player;
import com.back.backend.rest.dto.PlayerDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerMapper {
    public PlayerDTO mapToDTO(Player player) {
        PlayerDTO playerDTO = new PlayerDTO();

        playerDTO.setId(player.getId());
        playerDTO.setName(player.getName());

        if (player.getRoom() != null) {
            playerDTO.setRoomId(player.getRoom().getId());
        }

        return playerDTO;
    }

    public List<PlayerDTO> mapToDTOList(List<Player> playerList) {
        List<PlayerDTO> playerDTOList = new ArrayList<>();

        for (Player player : playerList) {
            playerDTOList.add(this.mapToDTO(player));
        }

        return playerDTOList;
    }
}
