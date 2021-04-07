package com.softvision.consumer.domain;

public class Character {
    private String name;
    private String side;

    public Character(){}

    public Character(String name, String side) {
        this.name = name;
        this.side = side;
    }

    public String getName() {
        return name;
    }

    public String getSide() {
        return side;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSide(String side) {
        this.side = side;
    }
}
