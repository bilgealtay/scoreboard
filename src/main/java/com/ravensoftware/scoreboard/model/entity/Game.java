package com.ravensoftware.scoreboard.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by bilga on 20-11-2021
 */
@Entity
@Table(name = "GAMES")
public class Game implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GAMES_ID_SEQ")
    @SequenceGenerator(name = "GAMES_ID_SEQ", sequenceName = "GAMES_ID_SEQ", allocationSize = 1)
    private Long id;

    @OneToOne
    @JoinColumn(name = "TEAM_ID_1")
    @NotNull
    private Team team1;

    @OneToOne
    @JoinColumn(name = "TEAM_ID_2")
    @NotNull
    private Team team2;

    @Column(name = "SCORE_TEAM_1", nullable = false)
    private int scoreTeam1 = 0;

    @Column(name = "SCORE_TEAM_2", nullable = false)
    private int scoreTeam2 = 0;

    @Column(name = "GAME_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status gameStatus = Status.ACTIVE;

    public Game() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
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
        Game game = (Game) o;
        return scoreTeam1 == game.scoreTeam1 &&
                scoreTeam2 == game.scoreTeam2 &&
                Objects.equals(id, game.id) &&
                Objects.equals(team1, game.team1) &&
                Objects.equals(team2, game.team2) &&
                gameStatus == game.gameStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, team1, team2, scoreTeam1, scoreTeam2, gameStatus);
    }
}
