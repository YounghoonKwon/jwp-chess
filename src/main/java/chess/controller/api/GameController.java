package chess.controller.api;

import chess.dto.*;
import chess.service.GameService;
import chess.utils.Serializer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/chessboard/{gameId}")
    public ResponseEntity<ChessBoardDto> loadGame(@PathVariable String gameId) {
        return ResponseEntity.ok(Serializer.deserializeGameAsDTO(gameService.loadGame(gameId)));
    }

    @GetMapping("/turn/{gameId}")
    public ResponseEntity<TurnDto> turn(@PathVariable String gameId) {
        return ResponseEntity.ok(new TurnDto(gameService.turn(gameId)));
    }

    @PutMapping(path = "/move/{gameId}")
    public ResponseEntity<Void> move(@PathVariable String gameId, @RequestBody MoveDto moveDTO) {
        gameService.move(gameId, moveDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/finishById/{gameId}")
    public ResponseEntity<FinishDto> isFinished(@PathVariable String gameId) {
        return ResponseEntity.ok(new FinishDto(gameService.isFinished(gameId)));
    }

    @PostMapping("/finish/{gameId}")
    public ResponseEntity<Void> finish(@PathVariable String gameId) {
        gameService.finish(gameId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/scoreById/{gameId}")
    public ResponseEntity<ScoreDto> score(@PathVariable String gameId) {
        List<Double> scores = gameService.score(gameId);
        return ResponseEntity.ok(new ScoreDto(scores.get(0), scores.get(1)));
    }

    @PostMapping("/restart/{gameId}")
    public ResponseEntity<ChessBoardDto> restart(@PathVariable String gameId) {
        return ResponseEntity.ok(Serializer.deserializeGameAsDTO(gameService.restart(gameId)));
    }
}
