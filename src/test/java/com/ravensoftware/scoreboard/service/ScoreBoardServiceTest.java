package com.ravensoftware.scoreboard.service;

import com.ravensoftware.scoreboard.config.MessageConstants;
import com.ravensoftware.scoreboard.model.GameModel;
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
 * Created by bilga on 21-11-2021
 */
@RunWith(SpringRunner.class)
public class ScoreBoardServiceTest {

    @TestConfiguration
    static class ScoreBoardServiceImplTestContextConfiguration {

        @Bean
        public ScoreBoardService scoreBoardService() {
            return new ScoreBoardService();
        }
    }

    @Autowired
    ScoreBoardService underTest;

    @MockBean
    GameRepository gameRepository;
    @MockBean
    TeamRepository teamRepository;

    @Test
    public void saveTeam(){

        // create Team
        Team team = new Team();
        team.setId(1L);
        team.setName("Team Name");

        when(teamRepository.save(any())).thenReturn((team));

        ScoreBoardResponse expectedScoreBoardResponse = underTest.saveTeam(team);

        Assert.assertEquals(expectedScoreBoardResponse.getResponseObject(), team);

    }

    @Test
    public void saveGame(){

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

        when(teamRepository.findById(any())).thenReturn(java.util.Optional.of(team1));
        when(teamRepository.findById(any())).thenReturn(java.util.Optional.of(team2));

        ScoreBoardResponse expectedScoreBoardResponse = underTest.saveGame(game);

        Assert.assertEquals(expectedScoreBoardResponse.getResponseObject(), new GameModel(game));

    }

    @Test
    public void findGame(){

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

        when(gameRepository.findById(any())).thenReturn(java.util.Optional.of(game));

        ScoreBoardResponse expectedScoreBoardResponse = underTest.findGame(game.getId());

        Assert.assertEquals(expectedScoreBoardResponse.getResponseObject(), new GameModel(game));

    }

    @Test
    public void findGame_should_return_Error_Message(){

        when(gameRepository.findById(any())).thenReturn(java.util.Optional.empty());

        ScoreBoardResponse expectedScoreBoardResponse = underTest.findGame(1L);

        Assert.assertEquals(expectedScoreBoardResponse.getErrorMessage(), MessageConstants.GAME_NOT_EXIST);

    }

    @Test
    public void saveGame_should_return_Error_Message_When_There_Is_An_Active_Game_Between_Two_Teams(){

        Team team1 = new Team();
        team1.setId(1L);
        team1.setName("Team Name 1");

        Team team2 = new Team();
        team2.setId(2L);
        team2.setName("Team Name 2");

        Game game = new Game();
        game.setId(1L);
        game.setTeam1(team1);
        game.setTeam2(team2);

        when(teamRepository.findById(any())).thenReturn(java.util.Optional.of(team1));
        when(teamRepository.findById(any())).thenReturn(java.util.Optional.of(team2));
        when(gameRepository.findActiveGameBetweenTwoTeams(any(), any())).thenReturn(game);

        ScoreBoardResponse expectedScoreBoardResponse = underTest.saveGame(game);

        Assert.assertEquals(expectedScoreBoardResponse.getErrorMessage(), MessageConstants.GAME_IS_EXIST);

    }
}
