package com.back.backend.rest.dto;

public class OpponentDTO {
    private int cardCount;
    private String name;
    private Long id;

    public OpponentDTO(int cardCount, String name, Long id) {
        this.cardCount = cardCount;
        this.name = name;
        this.id = id;
    }

    public OpponentDTO() {
    }

    public int getCardCount() {
        return cardCount;
    }

    public void setCardCount(int cardCount) {
        this.cardCount = cardCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
