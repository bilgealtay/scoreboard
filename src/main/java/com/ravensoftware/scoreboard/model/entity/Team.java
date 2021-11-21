package com.ravensoftware.scoreboard.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by bilga on 20-11-2021
 */
@Entity
@Table(name = "TEAMS")
public class Team implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEAMS_ID_SEQ")
    @SequenceGenerator(name = "TEAMS_ID_SEQ", sequenceName = "TEAMS_ID_SEQ", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "GAME_ID")
    private Game game;

    public Team() {
    }

    public Team(String name) {
        this.name = name;
    }

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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(id, team.id) &&
                Objects.equals(name, team.name) &&
                Objects.equals(game, team.game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, game);
    }
}
