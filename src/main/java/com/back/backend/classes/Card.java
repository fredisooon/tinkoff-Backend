package com.back.backend.classes;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String cardValue;
    private String color;
    private String img;

    public Card(Integer id, String cardValue, String color, String img) {
        this.id = id;
        this.cardValue = cardValue;
        this.color = color;
        this.img = img;
    }

    public Card() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardValue() {
        return cardValue;
    }

    public void setCardValue(String cardValue) {
        this.cardValue = cardValue;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
