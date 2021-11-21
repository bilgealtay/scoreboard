package com.ravensoftware.scoreboard.controller;

import com.ravensoftware.scoreboard.model.ScoreBoardModel;
import com.ravensoftware.scoreboard.model.entity.Game;
import com.ravensoftware.scoreboard.model.entity.Team;
import com.ravensoftware.scoreboard.service.ScoreBoardService;
import com.ravensoftware.scoreboard.service.ScoreBoardUpdateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by bilga on 20-11-2021
 */
@RestController
@CrossOrigin
@RequestMapping(path = "/")
public class ScoreBoardController {

    private ScoreBoardService scoreBoardService;
    private ScoreBoardUpdateService scoreBoardUpdateService;

    public ScoreBoardController(ScoreBoardService scoreBoardService, ScoreBoardUpdateService scoreBoardUpdateService) {
        this.scoreBoardService = scoreBoardService;
        this.scoreBoardUpdateService = scoreBoardUpdateService;
    }

    // save  Team
    @PostMapping(path = "teams")
    public ResponseEntity save(@RequestBody @NotNull @Valid Team team) {
        return ResponseEntity.ok(scoreBoardService.saveTeam(team));
    }

    // save game
    @PostMapping(path = "games")
    public ResponseEntity saveGame(@RequestBody @NotNull @Valid Game game) {
        return ResponseEntity.ok(scoreBoardService.saveGame(game));
    }

    // find game by id
    @GetMapping("games/{id}")
    public ResponseEntity findById(@PathVariable("id") @NotNull Long id) {
        return ResponseEntity.ok(scoreBoardService.findGame(id));
    }

    // find game by id
    @PutMapping("games/score")
    public ResponseEntity findById(@RequestBody @NotNull @Valid ScoreBoardModel scoreBoardModel) {
        return ResponseEntity.ok(scoreBoardUpdateService.updateScore(scoreBoardModel));
    }

}
