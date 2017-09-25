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
@RequestMapping("/player")
public class PlayerController {

	private static final int UUID_DURATION_TIME = 1;

	static Map<String, LocalDateTime> uuidTime = new HashMap<String, LocalDateTime>();
	static Map<String, Player> uuidPlayer = new HashMap<String, Player>();

	@RequestMapping("/")
	public String home() {
		String message="";
		for (Player tempPlayer : PlayerDatabase.getPlayersFromDatabase()) {
			message+="<p>" + tempPlayer.getLogin() + " Points: " + tempPlayer.showPoints() + "</p>";
		}
		return message;
	}

	@PostMapping("/login")
	public String login(@RequestBody Player player) {
		String uuid = generateUuid();
		String message = "Invalid login or password!";
		if (!verifyPlayer(player)) {
			return message;
		}

		if (!uuidPlayer.containsValue(player)) {
			Player.createPlayer(player);
			uuidPlayer.put(uuid, player);			
			message = uuid;
		} else {
			message = "You are already logged!";
		}

		return message;
	}

	private String generateUuid() {
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

	private void validateUuid(String uuid) {
		LocalDateTime now = LocalDateTime.now();
		try {
			if (!uuidTime.get(uuid).isAfter(now)) {
				uuidTime.remove(uuid);
				Player.removePlayer(uuidPlayer.get(uuid));
				uuidPlayer.remove(uuid);
				throw new RuntimeException("Uuid expired!");
			}
		} catch (java.lang.NullPointerException exception) {
			throw new RuntimeException("You are not logged!");
		}
	}

	private boolean verifyPlayer(Player player) {

		for (Player tempPlayer : PlayerDatabase.getPlayersFromDatabase()) {
			if (tempPlayer.equals(player)) {
				return true;
			}
		}
		return false;
	}

	@RequestMapping("/getPoints")
	public String getPoints(@RequestParam(value = "") String uuid) {
		Integer points = 0;
		validateUuid(uuid);
		
		for (Player tempPlayer : PlayerDatabase.getPlayersFromDatabase()) {
			if (tempPlayer.equals(uuidPlayer.get(uuid))) {
				tempPlayer.increasePoints();
				points = tempPlayer.showPoints();				
			}
		}
		
		return "You have actually :" + points + " points.";
	}


	@DeleteMapping("/logout")
	public String logout(@RequestParam(value = "") String uuid) {

		if (uuidPlayer.containsKey(uuid)) {
			String login = uuidPlayer.get(uuid).getLogin();
			Player.removePlayer(uuidPlayer.get(uuid));
			uuidTime.remove(uuid);
			uuidPlayer.remove(uuid);
			return String.format("Player %s logout successfully!", login);
		}
		return "Player is not logged in!";
	}

}
