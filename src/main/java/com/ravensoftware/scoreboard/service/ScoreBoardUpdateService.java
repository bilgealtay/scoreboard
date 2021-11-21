package com.ravensoftware.scoreboard.service;

import com.ravensoftware.scoreboard.base.BaseControl;
import com.ravensoftware.scoreboard.config.MessageConstants;
import com.ravensoftware.scoreboard.model.GameModel;
import com.ravensoftware.scoreboard.model.ScoreBoardModel;
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
public class ScoreBoardUpdateService extends BaseControl {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private TeamRepository teamRepository;

    public ScoreBoardResponse updateScore(ScoreBoardModel scoreBoardModel){

        Optional<Game> game = gameRepository.findById(scoreBoardModel.getGameId());
        game.ifPresentOrElse(
            (value) -> {
                Optional<Team> team = teamRepository.findById(scoreBoardModel.getTeamId());
                if (team.isEmpty()){
                    throwMessageException(MessageConstants.TEAM_NOT_EXIST);
                }

                if (value.getTeam1().getId().equals(scoreBoardModel.getTeamId())){
                    value.setScoreTeam1(value.getScoreTeam1() + 1);
                } else if (value.getTeam2().getId().equals(scoreBoardModel.getTeamId())){
                    value.setScoreTeam2(value.getScoreTeam2() + 1);
                }
                gameRepository.save(value);

            }, () -> throwMessageException(MessageConstants.GAME_NOT_EXIST));
        return createSuccessResponse(new GameModel(game.get()));
    }

    private ScoreBoardResponse createSuccessResponse(Object object){
        ScoreBoardResponse response = new ScoreBoardResponse();
        response.setSuccess(true);
        response.setResponseObject(object);
        return response;
    }

}
