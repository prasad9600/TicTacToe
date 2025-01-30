package models;

import exceptions.DuplicateBotFoundException;
import exceptions.DuplicateSymbolException;
import exceptions.PlayerCountException;
import strategies.WinningStrategy;
import validation.Validate;

import java.util.ArrayList;
import java.util.List;

import static validation.Validate.*;

public class Game {
    private List<Player> players;
    private Board board;
    private List<Move> moves;
    private Player winner;
    private GameState gameState;
    private int nextMovePlayerIndex;
    private List<WinningStrategy> winningStrategies;

    private Game(List<Player> players, int size, List<WinningStrategy> winningStrategies) {
        this.players = players;
        this.winningStrategies = winningStrategies;
        this.moves=new ArrayList<>();
        this.board=new Board(size);
        this.gameState=GameState.IN_PROGRESS;
    }


    public static class Builder {
        private List<Player> players;
        private int size;
        private List<WinningStrategy> winningStrategies;

        public Builder() {
            players = new ArrayList<>();
            winningStrategies = new ArrayList<>();

        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public Builder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        public Game build() throws PlayerCountException, DuplicateSymbolException, DuplicateBotFoundException {
            Validate validate = new Validate();
            validate.validatePlayerCount(players, size);
            validate.validateSymbolUniqueness(players);
            validate.BotCount(players);
            return new Game(players, size, winningStrategies);


        }
    }

        public static Builder getBuilder() {
            return new Builder();
        }

        public List<Player> getPlayers() {
            return players;
        }

        public void setPlayers(List<Player> players) {
           this.players = players;
        }


        public Board getBoard() {
            return board;
        }

        public void setBoard(Board board) {
            this.board = board;
        }

        public List<Move> getMoves() {
            return moves;
        }

        public void setMoves(List<Move> moves) {
            this.moves = moves;
        }

        public Player getWinner() {
            return winner;
        }

        public void setWinner(Player winner) {
            this.winner = winner;
        }

        public GameState getGameState() {
            return gameState;
        }

        public void setGameState(GameState gameState) {
            this.gameState = gameState;
        }

        public int getNextMovePlayerIndex() {
            return nextMovePlayerIndex;
        }

        public void setNextMovePlayerIndex(int nextMovePlayerIndex) {
            this.nextMovePlayerIndex = nextMovePlayerIndex;
        }

        public List<WinningStrategy> getWinningStrategy() {
            return winningStrategies;
        }

        public void setWinningStrategy(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
        }

        public void makeMove(){
            Player currentMovePlayer = players.get(nextMovePlayerIndex);
            System.out.println("It is " + currentMovePlayer.getName()+"'s turn, " +
                    "Please make a move");

            Move move = currentMovePlayer.makeMove(board);
            if(!validate(move)){
                System.out.println("It is not a valid move");
                return;
            }

            int row = move.getCell().getRow();
            int col = move.getCell().getCol();

            Cell cellToUpdate = board.getBoard().get(row).get(col);
            cellToUpdate.setCellState(CellState.FILLED);
            cellToUpdate.setPlayer(currentMovePlayer);

            moves.add(new Move(cellToUpdate,currentMovePlayer));

            nextMovePlayerIndex++;
            nextMovePlayerIndex %= players.size();

            if(checkWinner(move)){
                gameState=GameState.WINNER;
                winner=currentMovePlayer;
            }else if(moves.size() == board.getSize()* board.getSize()){
                gameState=GameState.DRAW;
            }

        }

        public void undo(){
        if(moves.size()==0){
            System.out.println("Board is Empty");
            return;
        }

        Move lastMove = moves.get(moves.size()-1);
        moves.remove(lastMove);

        Cell cell = lastMove.getCell();
        cell.setCellState(CellState.EMPTY);
        cell.setPlayer(null);

        nextMovePlayerIndex--;

        for(WinningStrategy winningStrategy : winningStrategies){
            winningStrategy.handleUndo(board,lastMove);
        }
     }

     public void printBoard(){
        board.printBoard();
     }

     private boolean validate(Move move){
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        if(row>= board.getSize()){
            return false;
        }

        if(col>= board.getSize()){
            return false;
        }

        if(board.getBoard().get(row).get(col)
                .getCellState().equals(CellState.EMPTY)){
            return true;
        }
        return false;
     }

     private boolean checkWinner(Move move){
        for(WinningStrategy winningStrategy : winningStrategies){
            if(winningStrategy.checkWinner(board,move)){
                return true;
            }
        }
        return false;
     }






}

