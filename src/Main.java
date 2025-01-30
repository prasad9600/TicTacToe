import Controller.GameController;
import exceptions.DuplicateBotFoundException;
import exceptions.DuplicateSymbolException;
import exceptions.PlayerCountException;
import models.*;
import strategies.ColWinningStrategy;
import strategies.DiaWinningStrategy;
import strategies.RowWinningStrategy;
import strategies.WinningStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args)  {
        GameController gameController = new GameController();
        Scanner scanner = new Scanner(System.in);

        List<Player> players = new ArrayList<Player>();

        players.add(
                new Player(1L,"Prasad", new Symbol('X'),
                        PlayerType.HUMAN)

                );

        players.add(
                new Bot(2L,
                        new Symbol('O'),
                        "bot",
                        BotDifficultyLevel.EASY)

                        );

        List<WinningStrategy> winningStrategies = new ArrayList<>();
        winningStrategies.add(new ColWinningStrategy());
        winningStrategies.add(new RowWinningStrategy());
        winningStrategies.add(new DiaWinningStrategy());

        Game game =null;

        try {
            game = gameController.startGame(players,3,winningStrategies);
        } catch (PlayerCountException e) {
            throw new RuntimeException(e);
        } catch (DuplicateSymbolException e) {
            throw new RuntimeException(e);
        } catch (DuplicateBotFoundException e) {
            throw new RuntimeException(e);
        }


        while(gameController.checkStatus(game)==GameState.IN_PROGRESS){
            gameController.printBoard(game);

            System.out.println("Do you want to undo? (y/n)");
            String answer = scanner.next();

            if(answer.equalsIgnoreCase("y")){
                gameController.undo(game);
                continue;
            }
            gameController.makeMove(game);

        }

        if(gameController.checkStatus(game)==GameState.WINNER){
            System.out.println("Winner is: " + gameController.getWinner(game).getName());

        }else{
            System.out.println("Game is Drawn");
        }



    }
}