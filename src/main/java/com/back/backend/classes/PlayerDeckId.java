package com.back.backend.classes;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PlayerDeckId implements Serializable {
    @Column(name = "game_id")
    private Long gameId;

    @Column(name = "player_id")
    private Long playerId;

    public PlayerDeckId(Long gameId, Long playerId) {
        this.gameId = gameId;
        this.playerId = playerId;
    }

    public PlayerDeckId() {

    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerDeckId that = (PlayerDeckId) o;
        return Objects.equals(gameId, that.gameId) && Objects.equals(playerId, that.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, playerId);
    }
}
