package com.ravensoftware.scoreboard.repository;

import com.ravensoftware.scoreboard.model.entity.Game;
import com.ravensoftware.scoreboard.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by bilga on 20-11-2021
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("SELECT c FROM Game c WHERE c.gameStatus = 'ACTIVE' and ((c.team1 =:team1 and c.team2=:team2) or (c.team1 =:team2 and c.team2=:team1))")
    Game findActiveGameBetweenTwoTeams(Team team1, Team team2);
}
