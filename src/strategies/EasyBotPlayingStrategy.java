package strategies;

import models.*;

import java.util.List;

public class EasyBotPlayingStrategy implements BotPlayingStrategy{
    @Override
    public Move makeMove(Board board, Bot bot) {
            for(List<Cell> row: board.getBoard()) {
                for(Cell cell: row) {
                    if(cell.getCellState().equals(CellState.EMPTY)) {
                        return new Move(cell, null);
                    }
                }
            }

            return null;
    }

}
