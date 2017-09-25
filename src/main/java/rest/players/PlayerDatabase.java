package rest.players;

import java.util.ArrayList;
import java.util.List;

public final class PlayerDatabase {

	private static List<Player> players = new ArrayList<>();
	private static List<Player> gameMasters = new ArrayList<>();
	private static PlayerDatabase instance = null;
	
	 private  PlayerDatabase(){
		players.add(new Player("muszkie","qwerty"));
		players.add(new Player("guest","qwerty"));
		gameMasters.add(new Player("gm","gm123",true));
	}
	
	public static PlayerDatabase connect(){
		if(instance == null) {
	         instance = new PlayerDatabase();
	      }
	      return instance;
	}
	public static List<Player> getPlayersFromDatabase(){
		return players;
	}
	public static List<Player> getGameMastersFromDatabase(){
		return gameMasters;
	}
	
	public static void createPlayer(Player player){
		if(player.isGameMaster()){
			gameMasters.add(player);
		}
		players.add(player);
	}
	
	
	
}
