package com.ravensoftware.scoreboard.model;

import com.ravensoftware.scoreboard.model.entity.Game;
import com.ravensoftware.scoreboard.model.entity.Status;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by bilga on 20-11-2021
 */
public class GameModel implements Serializable {

    private Long id;
    @NotNull
    private Long team1Id;
    @NotNull
    private Long team2Id;
    private int scoreTeam1;
    private int scoreTeam2;
    private Status gameStatus;

    public GameModel() {
        // empty
    }

    public GameModel(@NotNull Long team1Id, @NotNull Long team2Id) {
        this.team1Id = team1Id;
        this.team2Id = team2Id;
    }

    public GameModel(Game game) {
        this.id = game.getId();
        this.team1Id = game.getTeam1().getId();
        this.team2Id = game.getTeam2().getId();
        this.scoreTeam1 = game.getScoreTeam1();
        this.scoreTeam2 = game.getScoreTeam2();
        this.gameStatus = game.getGameStatus();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeam1Id() {
        return team1Id;
    }

    public void setTeam1Id(Long team1Id) {
        this.team1Id = team1Id;
    }

    public Long getTeam2Id() {
        return team2Id;
    }

    public void setTeam2Id(Long team2Id) {
        this.team2Id = team2Id;
    }

    public int getScoreTeam1() {
        return scoreTeam1;
    }

    public void setScoreTeam1(int scoreTeam1) {
        this.scoreTeam1 = scoreTeam1;
    }

    public int getScoreTeam2() {
        return scoreTeam2;
    }

    public void setScoreTeam2(int scoreTeam2) {
        this.scoreTeam2 = scoreTeam2;
    }

    public Status getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(Status gameStatus) {
        this.gameStatus = gameStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameModel gameModel = (GameModel) o;
        return scoreTeam1 == gameModel.scoreTeam1 &&
                scoreTeam2 == gameModel.scoreTeam2 &&
                Objects.equals(id, gameModel.id) &&
                Objects.equals(team1Id, gameModel.team1Id) &&
                Objects.equals(team2Id, gameModel.team2Id) &&
                gameStatus == gameModel.gameStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, team1Id, team2Id, scoreTeam1, scoreTeam2, gameStatus);
    }
}
