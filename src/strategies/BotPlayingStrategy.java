package strategies;

import models.Board;
import models.Bot;
import models.Move;

public interface BotPlayingStrategy {
    public Move makeMove(Board board, Bot bot);
}
