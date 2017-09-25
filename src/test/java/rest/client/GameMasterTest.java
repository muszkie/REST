package rest.client;



import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import rest.controllers.Application;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GameMasterTest {

	private static final String NEW_GAME_MASTER = "newGameMaster";
	private static String uuid;
	private static String newGameMasterUuid;
	private static String[] args;
	private final RestClientForGameMaster client = new RestClientForGameMaster();

	@BeforeClass
	public static void setUp() {
		args = new String[0];
		Application.main(args);
	}

	@Test
	public void test01HomePageTest() {
		// when
		String message = client.home().readEntity(String.class);
		// then
		Assertions.assertThat(message).contains("Game Master homepage.");
	}

	@Test
	public void test02LoginFailedTest() {
		// when
		String message = client.gameMasterLogin(NEW_GAME_MASTER, NEW_GAME_MASTER).readEntity(String.class);
		// then
		Assertions.assertThat(message).isEqualTo("Invalid login or password!");
	}

	@Test
	public void test03LoginSuccessTest() {
		// when
		uuid = client.gameMasterLogin("gm", "gm123").readEntity(String.class);
		// then
		Long.parseLong(uuid);
	}

	@Test
	public void test04LogoutFailed() {
		// given
		String uuid = "1111111111";
		// when
		String message = client.gameMasterLogout(uuid).readEntity(String.class);
		// then
		Assertions.assertThat(message).isEqualTo("Game Master is not logged in!");
	}

	@Test
	public void test05CreatePlayer() {
		// when
		int response = client.createPlayer(uuid,"newPlayer", "newPlayer").getStatus();
		// then
		Assertions.assertThat(response).isEqualTo(200);
	}

	@Test
	public void test06CreateGameMaster() {
		// when
		String response = client.createGameMaster(uuid,NEW_GAME_MASTER, NEW_GAME_MASTER).readEntity(String.class);
		// then
		Assertions.assertThat(response).isEqualTo("Player added!");
	}
	@Test
	public void test07CreateGameMasterFailed() {
		// when
		String response = client.createGameMaster(uuid,NEW_GAME_MASTER, NEW_GAME_MASTER).readEntity(String.class);
		// then
		Assertions.assertThat(response).isEqualTo("Player duplicate!");
	}

	@Test
	public void test08GameMasterLoginSuccess() {
		// when
		newGameMasterUuid = client.gameMasterLogin(NEW_GAME_MASTER, NEW_GAME_MASTER).readEntity(String.class);
		// then
		Long.parseLong(newGameMasterUuid);
	}

	@Test
	public void test09PlayerLoginSuccess() {
		// given
		RestClientForPlayer client = new RestClientForPlayer();
		// when
		String uuid = client.playerLogin("newPlayer", "newPlayer").readEntity(String.class);
		// then
		Long.parseLong(uuid);
	}
	@Test
	public void test10LogoutSuccess() {
		// when
		String message = client.gameMasterLogout(newGameMasterUuid).readEntity(String.class);
		// then
		Assertions.assertThat(message).contains("logout successfully!");
	}

}
