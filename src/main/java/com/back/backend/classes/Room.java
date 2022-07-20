package com.back.backend.classes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer count;
    private Integer maxCount;
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Game game;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "room")
    private List<Player> players = new ArrayList<>();


    public void addPlayer(Player player) {
        this.players.add(player);
        this.count += 1;
        player.setRoom(this);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
        this.count -= 1;
        player.setRoom(null);
    }

    public List<Player> getUsers() {
        return players;
    }

    public void setUsers(List<Player> player) {
        this.players = player;
    }

    public Room(Long id, Integer count, Integer maxCount, String name, Game game) {
        this.id = id;
        this.count = count;
        this.maxCount = maxCount;
        this.name = name;
        this.game = game;
    }

    public Room() {

    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
