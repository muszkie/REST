package rest.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import rest.players.Player;

public class RestClientForPlayer {

	private static final String HOST = "http://localhost:8080/player/";

	public Response playerLogin(String login, String password) {
		Client client = ClientBuilder.newClient();
		Player player = new Player(login, password);
		Response response = client.target(HOST + "login").request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(player, MediaType.APPLICATION_JSON), Response.class);

		return response;

	}
	
	public Response playerLogout(String uuid){
		Client client = ClientBuilder.newClient();
		Response response = client.target(HOST + "logout?uuid="+uuid).request(MediaType.APPLICATION_JSON)
				.delete();
		return response;
	}

	public Response home() {
		Client client = ClientBuilder.newClient();
		Response response = client.target(HOST).request(MediaType.APPLICATION_JSON).get(Response.class);
		return response;

	}
	public Response getPoints(String uuid) {
		Client client = ClientBuilder.newClient();
		Response response = client.target(HOST+"getPoints?uuid="+uuid).request(MediaType.APPLICATION_JSON).get(Response.class);
		return response;

	}

}