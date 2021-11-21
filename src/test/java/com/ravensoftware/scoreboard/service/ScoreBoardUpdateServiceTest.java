package com.ravensoftware.scoreboard.service;

import com.ravensoftware.scoreboard.model.GameModel;
import com.ravensoftware.scoreboard.model.ScoreBoardModel;
import com.ravensoftware.scoreboard.model.ScoreBoardResponse;
import com.ravensoftware.scoreboard.model.entity.Game;
import com.ravensoftware.scoreboard.model.entity.Team;
import com.ravensoftware.scoreboard.repository.GameRepository;
import com.ravensoftware.scoreboard.repository.TeamRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by bilga on 20-11-2021
 */
@RunWith(SpringRunner.class)
public class ScoreBoardUpdateServiceTest {

    @TestConfiguration
    static class ScoreBoardUpdateServiceImplTestContextConfiguration {

        @Bean
        public ScoreBoardUpdateService scoreBoardUpdateService() {
            return new ScoreBoardUpdateService();
        }
    }

    @Autowired
    ScoreBoardUpdateService underTest;

    @MockBean
    GameRepository gameRepository;
    @MockBean
    TeamRepository teamRepository;

    @Test
    public void updateScore(){

        // create Teams
        Team team1 = new Team();
        team1.setId(1L);
        team1.setName("Team Name 1");
        Team team2 = new Team();
        team2.setId(2L);
        team2.setName("Team Name 2");


        // create Game
        Game game = new Game();
        game.setId(1L);
        game.setTeam1(team1);
        game.setTeam2(team2);


        ScoreBoardModel scoreBoardModel = new ScoreBoardModel(game.getId(), team1.getId());


        when(teamRepository.findById(any())).thenReturn(java.util.Optional.of(team1));

        when(gameRepository.findById(any())).thenReturn(java.util.Optional.of(game));

        ScoreBoardResponse expectedScoreBoardResponse = underTest.updateScore(scoreBoardModel);

        Assert.assertEquals(expectedScoreBoardResponse.getResponseObject(), new GameModel(game));

    }
}
