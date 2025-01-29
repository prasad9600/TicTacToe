package validation;

import exceptions.DuplicateBotFoundException;
import exceptions.DuplicateSymbolException;
import exceptions.PlayerCountException;
import models.Player;
import models.PlayerType;
import models.Symbol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Validate {

    public void validatePlayerCount(List<Player> players, int size) throws PlayerCountException {
        if(players.size()!=size-1){
            throw new PlayerCountException("Not a perfect sized board to play");
        }
    }

    public  void validateSymbolUniqueness(List<Player> players) throws DuplicateSymbolException {
        Map<Symbol,Integer> map = new HashMap<>();
        for(Player p : players){
            Symbol symbol = p.getSymbol();
            map.put(symbol,map.getOrDefault(symbol,0)+1);

            if(map.get(symbol)>1){
                throw new DuplicateSymbolException("Two players using same symbol");
            }
        }
    }

    public  void BotCount(List<Player> players) throws DuplicateBotFoundException {
        int botCount = 0;
        for(Player p : players){
            PlayerType playerType = p.getPlayerType();
            if(playerType==PlayerType.BOT){
                botCount++;
            }
            if(botCount>1){
                throw new DuplicateBotFoundException("More than 1 Bot Found");
            }
        }
    }
}
