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
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("SELECT c FROM Team c WHERE LOWER(TRIM(c.name))=LOWER(TRIM(:name))")
    Team findByName(String name);

    @Query("SELECT c FROM Team c WHERE c.game =:game and LOWER(TRIM(c.name))=LOWER(TRIM(:name)) and c.game.gameStatus = 'ACTIVE'")
    Team findByGameAndName(Game game, String name);
}
