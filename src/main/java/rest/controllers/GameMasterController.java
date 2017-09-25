package rest.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rest.players.Player;
import rest.players.PlayerDatabase;

@RestController
@RequestMapping("/gameMaster")
public class GameMasterController {
	private static final int UUID_DURATION_TIME = 2;


	Map<String, LocalDateTime> uuidTime = new HashMap<String, LocalDateTime>();
	Map<String, Player> uuidGameMaster = new HashMap<String, Player>();

	@RequestMapping("/")
	public String home() {
		return "Game Master homepage.";
	}

	@PostMapping("/login")
	public String login(@RequestBody Player player) {
		String uuid = generateUuid();
		player.setGameMaster(true);
		String message = "Invalid login or password!";
		if (!verifyGameMaster(player)) {
			return message;
		}
		if (!uuidGameMaster.containsValue(player)) {
			Player.createPlayer(player);
			uuidGameMaster.put(uuid, player);
			message = uuid;
		} else {
			message = "You are already logged!";
		}

		return message;
	}

	@DeleteMapping("/logout")
	public String logout(@RequestParam(value = "") String uuid) {
		if (uuidGameMaster.containsKey(uuid)) {
			String login = uuidGameMaster.get(uuid).getLogin();
			Player.removePlayer(uuidGameMaster.get(uuid));
			uuidTime.remove(uuid);
			uuidGameMaster.remove(uuid);
			return String.format("Game Master %s logout successfully!", login);
		}
		return "Game Master is not logged in!";
	}

	@PostMapping("/createPlayer")
	public String createPlayer(@RequestBody Player player, @RequestParam(value = "") String uuid) {
		if(checkIfPlayerExistInDatabase(player)){
			return "Player duplicate!";
		}
		PlayerDatabase.createPlayer(player);
		return "Player added!";
	}

	private boolean checkIfPlayerExistInDatabase(Player player) {
		if (player.isGameMaster()){
			for (Player tempPlayer : PlayerDatabase.getGameMastersFromDatabase()) {
				if (tempPlayer.equals(player)) {
					return true;
				}
			}
			return false;
		}
		for (Player tempPlayer : PlayerDatabase.getPlayersFromDatabase()) {
			if (tempPlayer.equals(player)) {
				return true;
			}
		}
		return false;

	}

	public String generateUuid() {
		StringBuffer uuid = new StringBuffer();
		Random generator = new Random();
		for (int i = 0; i < 10; i++) {
			int number = generator.nextInt(10);
			uuid.append(number);
		}
		LocalDateTime uuidValidationTime = LocalDateTime.now();
		uuidValidationTime = uuidValidationTime.plusMinutes(UUID_DURATION_TIME);
		uuidTime.put(uuid.toString(), uuidValidationTime);
		return uuid.toString();
	}

	private boolean verifyGameMaster(Player player) {
		for (Player tempPlayer : PlayerDatabase.getGameMastersFromDatabase()) {
			if (tempPlayer.equals(player)) {
				return true;
			}
		}
		return false;
	}

}
