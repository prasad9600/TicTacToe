package models;

import strategies.BotPlayingStrategy;
import strategies.BotPlayingStrategyFactory;

public class Bot extends Player{
    private BotDifficultyLevel botDifficultyLevel;
    private BotPlayingStrategy botPlayingStrategy;

    public Bot(Long id,
               Symbol symbol,
               String name,
               BotDifficultyLevel botDifficultyLevel) {
        super(id,name,symbol,PlayerType.BOT);
        this.botDifficultyLevel = botDifficultyLevel;
        this.botPlayingStrategy = BotPlayingStrategyFactory.getBotPlayingStrategy(botDifficultyLevel);

    }


    public BotDifficultyLevel getBotDifficultyLevel() {
        return botDifficultyLevel;
    }

    public void setBotDifficultyLevel(BotDifficultyLevel botDifficultyLevel) {
        this.botDifficultyLevel = botDifficultyLevel;
    }

    public Move makeMove(Board board) {
        Move move = botPlayingStrategy.makeMove(board,this);
        move.setPlayer(this);
        return move;
    }
}
