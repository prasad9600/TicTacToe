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

   public static class Builder{
       private List<Player> players;
       private int size;
       private List<WinningStrategy> winningStrategies;

       public Builder (){
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
           validate.validatePlayerCount(players,size);
           validate.validateSymbolUniqueness(players);
           validate.BotCount(players);
           return new Game(players,size,winningStrategies);

       public List<Player> getPlayers() {
           return players;
       }

       public int getSize() {
           return size;
       }

       public List<WinningStrategy> getWinningStrategies() {
           return winningStrategies;
       }
   }
    public static Builder getBuilder(){
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
        return winningStrategy;
    }

    public void setWinningStrategy(List<WinningStrategy> winningStrategy) {
        this.winningStrategy = winningStrategy;
    }


}
