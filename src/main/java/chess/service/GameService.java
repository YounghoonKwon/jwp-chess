package chess.service;

import chess.domain.board.Position;
import chess.domain.game.ChessGame;
import chess.dto.MoveDto;
import chess.repository.ChessRepository;
import org.springframework.stereotype.Service;


@Service
public class GameService {

    private final ChessRepository chessRepository;

    public GameService(ChessRepository chessRepository) {
        this.chessRepository = chessRepository;
    }

    public void move(Long id, MoveDto moveDto) {
        ChessGame chessGame = chessRepository.loadGame(id);
        Position sourcePosition = Position.of(moveDto.getSource());
        Position targetPosition = Position.of(moveDto.getTarget());

        chessGame.move(sourcePosition, targetPosition);
        checkGameOver(id, chessGame);

        chessRepository.saveGame(id, chessGame);
    }

    private void checkGameOver(Long id, ChessGame chessGame) {
        if (chessGame.isGameOver()) {
            terminateGame(id);
        }
    }

    public ChessGame loadGame(Long id) {
        return chessRepository.loadGame(id);
    }

    public void terminateGame(Long id) {
        chessRepository.terminateGame(id);
    }

    public ChessGame restart(Long id) {
        ChessGame chessGame = new ChessGame();
        chessRepository.restart(id, chessGame);
        return chessRepository.loadGame(id);
    }
}
