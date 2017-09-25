package rest.client;

import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import rest.controllers.Application;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlayerTest {

	private RestClientForPlayer client = new RestClientForPlayer();
	private static String uuid;
	private static String[] args;

	@BeforeClass
	public static void setUp() {
		args = new String[0];
		Application.main(args);
	}


	@Test
	public void test1LoginFailedTest() {
		// when
		String message = client.playerLogin("newPlayer", "newPlayer").readEntity(String.class);
		// then
		Assertions.assertThat(message).isEqualTo("Invalid login or password!");
	}

	@Test
	public void test2LoginSuccessTest() {
		// when
		String message = client.playerLogin("muszkie", "qwerty").readEntity(String.class);
		uuid = message.substring(message.length() - 10, message.length());
		// then
		Long.parseLong(uuid);
	}

	@Test
	public void test3LogoutSuccess() {
		// when
		String message = client.playerLogout(uuid).readEntity(String.class);
		// then
		Assertions.assertThat(message).contains("logout successfully!");
	}

	@Test
	public void test4LogoutFailed() {
		// given
		String uuid = "1234567890";
		// when
		String message = client.playerLogout(uuid).readEntity(String.class);
		// then
		Assertions.assertThat(message).isEqualTo("Player is not logged in!");
	}

}
