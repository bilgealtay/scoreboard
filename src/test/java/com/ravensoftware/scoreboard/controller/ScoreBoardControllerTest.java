package com.ravensoftware.scoreboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ravensoftware.scoreboard.config.MessageConstants;
import com.ravensoftware.scoreboard.model.ScoreBoardModel;
import com.ravensoftware.scoreboard.model.ScoreBoardResponse;
import com.ravensoftware.scoreboard.model.entity.Game;
import com.ravensoftware.scoreboard.model.entity.Team;
import com.ravensoftware.scoreboard.service.ScoreBoardService;
import com.ravensoftware.scoreboard.service.ScoreBoardUpdateService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by bilga on 21-11-2021
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ScoreBoardController.class)
public class ScoreBoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScoreBoardService scoreBoardService;
    @MockBean
    private ScoreBoardUpdateService scoreBoardUpdateService;

    ObjectMapper mapper =  new ObjectMapper();

    @Test
    public void should_return_bad_request_when_team_body_is_empty() throws Exception {

        Team team = new Team();

        mockMvc.perform(post("/teams")
                .content(mapper.writeValueAsString(team))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void should_return_ok_when_team_body_is_not_empty() throws Exception {

        Team team = new Team();
        team.setName("Team Name");

        mockMvc.perform(post("/teams")
                .content(mapper.writeValueAsString(team))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void should_return_bad_request_when_game_body_is_empty() throws Exception {

        Game game = new Game();

        mockMvc.perform(post("/games")
                .content(mapper.writeValueAsString(game))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void should_return_bad_request_when_team1_is_empty() throws Exception {

        Team team2 = new Team();
        team2.setId(2L);
        team2.setName("Team Name 2");

        Game game = new Game();
        game.setId(1L);
        game.setTeam2(team2);

        mockMvc.perform(post("/games")
                .content(mapper.writeValueAsString(game))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void should_return_bad_request_when_team2_is_empty() throws Exception {

        Team team2 = new Team();
        team2.setId(2L);
        team2.setName("Team Name 2");

        Game game = new Game();
        game.setId(1L);
        game.setTeam2(team2);

        mockMvc.perform(post("/games")
                .content(mapper.writeValueAsString(game))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void should_return_bad_request_when_game_is_exist_between_two_teams() throws Exception {

        Team team1 = new Team();
        team1.setId(2L);
        team1.setName("Team Name 1");

        Team team2 = new Team();
        team2.setId(2L);
        team2.setName("Team Name 2");

        Game game = new Game();
        game.setId(1L);
        game.setTeam1(team1);
        game.setTeam2(team2);

        ScoreBoardResponse scoreBoardResponse = new ScoreBoardResponse();
        scoreBoardResponse.setSuccess(false);
        scoreBoardResponse.setErrorMessage(MessageConstants.GAME_IS_EXIST);

        when( scoreBoardService.saveGame( any() ) )
                .thenReturn(scoreBoardResponse);

        MvcResult result = mockMvc.perform(post("/games")
                .content(mapper.writeValueAsString(game))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ScoreBoardResponse content = mapper.readValue(result.getResponse().getContentAsString(), ScoreBoardResponse.class);

        Assert.assertEquals(content.getErrorMessage(), MessageConstants.GAME_IS_EXIST);
    }

    @Test
    public void should_return_ok_when_game_is_ok() throws Exception {

        Team team1 = new Team();
        team1.setId(2L);
        team1.setName("Team Name 1");

        Team team2 = new Team();
        team2.setId(2L);
        team2.setName("Team Name 2");

        Game game = new Game();
        game.setId(1L);
        game.setTeam1(team1);
        game.setTeam2(team2);

        mockMvc.perform(post("/games")
                .content(mapper.writeValueAsString(game))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void should_return_error_message_when_game_is_not_exist() throws Exception {

        ScoreBoardResponse scoreBoardResponse = new ScoreBoardResponse();
        scoreBoardResponse.setSuccess(false);
        scoreBoardResponse.setErrorMessage(MessageConstants.GAME_NOT_EXIST);

        when( scoreBoardService.findGame( any() ) )
                .thenReturn(scoreBoardResponse);

        MvcResult result = mockMvc.perform(get("/games/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ScoreBoardResponse content = mapper.readValue(result.getResponse().getContentAsString(), ScoreBoardResponse.class);

        Assert.assertEquals(content.getErrorMessage(), MessageConstants.GAME_NOT_EXIST);
    }

    @Test
    public void should_return_true_when_game_is_exist() throws Exception {

        Team team1 = new Team();
        team1.setId(2L);
        team1.setName("Team Name 1");

        Team team2 = new Team();
        team2.setId(2L);
        team2.setName("Team Name 2");

        Game game = new Game();
        game.setId(1L);
        game.setTeam1(team1);
        game.setTeam2(team2);

        ScoreBoardResponse scoreBoardResponse = new ScoreBoardResponse();
        scoreBoardResponse.setSuccess(true);
        scoreBoardResponse.setResponseObject(game);

        when( scoreBoardService.findGame( any() ) )
                .thenReturn(scoreBoardResponse);

        MvcResult result = mockMvc.perform(get("/games/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ScoreBoardResponse content = mapper.readValue(result.getResponse().getContentAsString(), ScoreBoardResponse.class);

        Assert.assertEquals(content.getSuccess(), true);
    }

    @Test
    public void should_return_error_message_when_team_not_exist() throws Exception {

        ScoreBoardModel scoreBoardModel = new ScoreBoardModel();
        scoreBoardModel.setGameId(1L);
        scoreBoardModel.setTeamId(1L);

        ScoreBoardResponse scoreBoardResponse = new ScoreBoardResponse();
        scoreBoardResponse.setSuccess(false);
        scoreBoardResponse.setErrorMessage(MessageConstants.TEAM_NOT_EXIST);

        when( scoreBoardUpdateService.updateScore( any() ) )
                .thenReturn(scoreBoardResponse);

        MvcResult result = mockMvc.perform(put("/games/score")
                .content(mapper.writeValueAsString(scoreBoardModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ScoreBoardResponse content = mapper.readValue(result.getResponse().getContentAsString(), ScoreBoardResponse.class);

        Assert.assertEquals(content.getErrorMessage(), MessageConstants.TEAM_NOT_EXIST);
    }

    @Test
    public void should_return_error_message_when_game_not_exist() throws Exception {

        ScoreBoardModel scoreBoardModel = new ScoreBoardModel();
        scoreBoardModel.setGameId(1L);
        scoreBoardModel.setTeamId(1L);

        ScoreBoardResponse scoreBoardResponse = new ScoreBoardResponse();
        scoreBoardResponse.setSuccess(false);
        scoreBoardResponse.setErrorMessage(MessageConstants.GAME_NOT_EXIST);

        when( scoreBoardUpdateService.updateScore( any() ) )
                .thenReturn(scoreBoardResponse);

        MvcResult result = mockMvc.perform(put("/games/score")
                .content(mapper.writeValueAsString(scoreBoardModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ScoreBoardResponse content = mapper.readValue(result.getResponse().getContentAsString(), ScoreBoardResponse.class);

        Assert.assertEquals(content.getErrorMessage(), MessageConstants.GAME_NOT_EXIST);
    }

    @Test
    public void should_return_bad_request_when_update_model_body_is_empty() throws Exception {

        ScoreBoardModel scoreBoardModel = new ScoreBoardModel();

        mockMvc.perform(put("/games/score")
                .content(mapper.writeValueAsString(scoreBoardModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void should_return_bad_request_when_update_model_game_id_is_empty() throws Exception {

        ScoreBoardModel scoreBoardModel = new ScoreBoardModel();
        scoreBoardModel.setTeamId(1L);

        mockMvc.perform(put("/games/score")
                .content(mapper.writeValueAsString(scoreBoardModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void should_return_bad_request_when_update_model_team_id_is_empty() throws Exception {

        ScoreBoardModel scoreBoardModel = new ScoreBoardModel();
        scoreBoardModel.setGameId(1L);

        mockMvc.perform(put("/games/score")
                .content(mapper.writeValueAsString(scoreBoardModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void should_return_game_after_update_score() throws Exception {

        ScoreBoardModel scoreBoardModel = new ScoreBoardModel();
        scoreBoardModel.setGameId(1L);
        scoreBoardModel.setTeamId(1L);

        ScoreBoardResponse scoreBoardResponse = new ScoreBoardResponse();
        scoreBoardResponse.setSuccess(true);

        when( scoreBoardUpdateService.updateScore( scoreBoardModel ) )
                .thenReturn(scoreBoardResponse);

        MvcResult result = mockMvc.perform(put("/games/score")
                .content(mapper.writeValueAsString(scoreBoardModel))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ScoreBoardResponse content = mapper.readValue(result.getResponse().getContentAsString(), ScoreBoardResponse.class);

        Assert.assertEquals(content.getSuccess(), true);
        Assert.assertEquals(content.getErrorMessage(), null);

    }
}
