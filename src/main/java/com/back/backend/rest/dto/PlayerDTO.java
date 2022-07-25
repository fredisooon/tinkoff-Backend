package com.back.backend.rest.dto;

public class PlayerDTO {
    private Long id;
    private String name;
    private Long roomId;

    public PlayerDTO(Long id, String name, Long roomId) {
        this.id = id;
        this.name = name;
        this.roomId = roomId;
    }

    public PlayerDTO() {

    }

    private Long roomId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}
