package com.ravensoftware.scoreboard.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by bilga on 20-11-2021
 */
public class ScoreBoardModel implements Serializable {

    @NotNull
    private Long gameId;
    @NotNull
    private Long teamId;

    public ScoreBoardModel() {
    }

    public ScoreBoardModel(@NotNull Long gameId, @NotNull Long teamId) {
        this.gameId = gameId;
        this.teamId = teamId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoreBoardModel that = (ScoreBoardModel) o;
        return Objects.equals(gameId, that.gameId) &&
                Objects.equals(teamId, that.teamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, teamId);
    }
}
