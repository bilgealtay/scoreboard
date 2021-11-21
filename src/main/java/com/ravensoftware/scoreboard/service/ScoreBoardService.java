package com.ravensoftware.scoreboard.service;

import com.ravensoftware.scoreboard.base.BaseControl;
import com.ravensoftware.scoreboard.config.MessageConstants;
import com.ravensoftware.scoreboard.model.GameModel;
import com.ravensoftware.scoreboard.model.ScoreBoardResponse;
import com.ravensoftware.scoreboard.model.entity.Game;
import com.ravensoftware.scoreboard.model.entity.Team;
import com.ravensoftware.scoreboard.repository.GameRepository;
import com.ravensoftware.scoreboard.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by bilga on 20-11-2021
 */
@Service
public class ScoreBoardService extends BaseControl {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private TeamRepository teamRepository;

    public ScoreBoardResponse saveTeam(Team team){

        if (team.getName() == null || team.getName().isEmpty()){
            throwMessageException(MessageConstants.TEAM_NAME);
        }
        teamRepository.save(team);

        return createSuccessResponse(team);
    }

    public ScoreBoardResponse saveGame(Game game){

        Optional<Team> team1 = teamRepository.findById(game.getTeam1().getId());
        team1.ifPresentOrElse(game::setTeam1, () -> throwMessageException(MessageConstants.TEAM_NOT_EXIST));

        Optional<Team> team2 = teamRepository.findById(game.getTeam2().getId());
        team2.ifPresentOrElse(game::setTeam2, () -> throwMessageException(MessageConstants.TEAM_NOT_EXIST));

        //if there is an active game between two teams, don't save the new game.
        if (gameRepository.findActiveGameBetweenTwoTeams(team1.get(), team2.get()) != null){
            return createErrorResponse(MessageConstants.GAME_IS_EXIST);
        }
        gameRepository.save(game);

        GameModel savedGame = new GameModel(game);

        return createSuccessResponse(savedGame);
    }

    public ScoreBoardResponse findGame(Long gameId){

        Optional<Game> game = gameRepository.findById(gameId);
        if (game.isEmpty()){
            return createErrorResponse(MessageConstants.GAME_NOT_EXIST);
        }
        return createSuccessResponse(new GameModel(game.get()));
    }

    private ScoreBoardResponse createSuccessResponse(Object object){
        ScoreBoardResponse response = new ScoreBoardResponse();
        response.setSuccess(true);
        response.setResponseObject(object);
        return response;
    }

    private ScoreBoardResponse createErrorResponse(String message){
        ScoreBoardResponse response = new ScoreBoardResponse();
        response.setSuccess(false);
        response.setErrorMessage(message);
        return response;
    }

}
