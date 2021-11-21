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
public class GameRepositoryTest {

    @Autowired
    private GameRepository underTest;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void should_find_no_game_if_repository_is_empty() {
        Iterable<Game> games = underTest.findAll();
        assertThat(games).isEmpty();
    }

    @Test
    public void should_store_game() {

        Team team1 = teamRepository.save(new Team("Team Name"));
        entityManager.persist(team1);

        Team team2 = teamRepository.save(new Team("Team Name2"));
        entityManager.persist(team2);

        Game game = new Game();
        game.setGameStatus(Status.ACTIVE);
        game.setTeam1(team1);
        game.setTeam2(team2);
        game.setScoreTeam1(1);
        game.setScoreTeam2(0);

        game = underTest.save(game);

        assertThat(game.getId() == null);
    }

}
