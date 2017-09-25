package rest.players;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private  String login;
	private String password;
	private boolean gameMaster;
	private Integer points = 0;

	private static List<Player> players = new ArrayList<>();

	public Player() {
	}

	public Player(String login, String password) {
		this.login = login;
		this.password = password;
		points = 0;
		this.gameMaster = false;
	}

	public Player(String login, String password, boolean gameMaster) {
		this.login = login;
		this.password = password;
		this.points = 0;
		this.gameMaster = gameMaster;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}
	
	public Integer showPoints() {
		if(!isGameMaster()){
		return points;
		}
		return null;
	}

	public boolean isGameMaster() {
		return gameMaster;
	}

	public void setGameMaster(boolean gameMaster) {
		this.gameMaster = gameMaster;
	}
	
	public void increasePoints() {
		points+=1;
	}

	public static List<Player> getPlayers() {
		return players;
	}

	public static void createPlayer(Player player) {
		players.add(player);
	}

	public static void removePlayer(Player player) {
		players.remove(player);
	}

	public static void replacePlayer(Player player) {
		removePlayer(player);
		createPlayer(player);
	}

	@Override
	public String toString() {
		return "Player [login=" + login + ", password=" + password + ", gameMaster=" + gameMaster + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	public boolean equalsLogin(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

}
