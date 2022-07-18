package com.back.backend.rest.dto;

public class CardDTO {
    private Integer id;
    private Integer value;
    private String color;
    private String linkToImg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLinkToImg() {
        return linkToImg;
    }

    public void setLinkToImg(String linkToImg) {
        this.linkToImg = linkToImg;
    }
}
