package rest.client;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import rest.players.Player;

public class RestClientForGameMaster {
	private static final String HOST = "http://localhost:8080/gameMaster/";

	Client client = ClientBuilder.newClient();
	
	public Response gameMasterLogin(String login, String password) {	
		Player player = new Player(login, password);
		Response response = client.target(HOST + "login").request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(player, MediaType.APPLICATION_JSON), Response.class);
		return response;
	}
	
	public Response gameMasterLogout(String uuid){
		Response response = client.target(HOST + "logout?uuid="+uuid).request(MediaType.APPLICATION_JSON)
				.delete();
		return response;
	}

	public Response home() {
		Response response = client.target(HOST).request(MediaType.APPLICATION_JSON).get(Response.class);
		return response;

	}

	public Response createPlayer(String uuid,String login, String password)
	{
		Player player = new Player(login,password);
		Response response = client.target(HOST + "createPlayer?uuid="+uuid).request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(player, MediaType.APPLICATION_JSON), Response.class);
		return response;
	}
	public Response createGameMaster(String uuid,String login, String password)
	{
		Player player = new Player(login,password,true);
		Response response = client.target(HOST + "createPlayer?uuid="+uuid).request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(player, MediaType.APPLICATION_JSON), Response.class);
		return response;
	}
}
