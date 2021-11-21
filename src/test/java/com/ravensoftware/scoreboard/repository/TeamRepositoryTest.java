package com.ravensoftware.scoreboard.repository;

import com.ravensoftware.scoreboard.model.entity.Game;
import com.ravensoftware.scoreboard.model.entity.Status;
import com.ravensoftware.scoreboard.model.entity.Team;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by bilga on 20-11-2021
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class TeamRepositoryTest {

    @Autowired
    private TeamRepository underTest;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void should_find_no_team_if_repository_is_empty() {
        Iterable<Team> teams = underTest.findAll();
        assertThat(teams).isEmpty();
    }

    @Test
    public void should_store_team() {
        Team team = underTest.save(new Team("Team Name"));

        assertThat(team).hasFieldOrPropertyWithValue("name", "Team Name");
    }

    @Test
    public void should_find_all_teams() {
        Team team1 = underTest.save(new Team("Team Name"));
        entityManager.persist(team1);

        Team team2 = underTest.save(new Team("Team Name2"));
        entityManager.persist(team2);

        Team team3 = underTest.save(new Team("Team Name3"));
        entityManager.persist(team3);

        Iterable<Team> teams = underTest.findAll();

        assertThat(teams).hasSize(3).contains(team1, team2, team3);
    }

    @Test
    public void should_find_team_by_game() {
        Team team1 = underTest.save(new Team("Team Name"));
        entityManager.persist(team1);

        Team team2 = underTest.save(new Team("Team Name2"));
        entityManager.persist(team2);

        Game game = new Game();
        game.setGameStatus(Status.ACTIVE);
        game.setTeam1(team1);
        game.setTeam2(team2);
        game.setScoreTeam1(1);
        game.setScoreTeam2(0);
        gameRepository.save(game);

        team1.setGame(game);
        team2.setGame(game);
        entityManager.merge(team1);
        entityManager.merge(team2);

        Team team = underTest.findByGameAndName(game, "Team Name");

        assertThat(team).hasFieldOrPropertyWithValue("name", "Team Name");
    }
}
