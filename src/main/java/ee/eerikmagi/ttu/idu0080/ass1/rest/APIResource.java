package ee.eerikmagi.ttu.idu0080.ass1.rest;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.jackson.JacksonFeature;

import ee.eerikmagi.ttu.idu0080.ass1.app.context.ObjectMapperProvider;
import ee.eerikmagi.ttu.idu0080.ass1.dao.GamesDAO;
import ee.eerikmagi.ttu.idu0080.ass1.model.db.Game;
import ee.eerikmagi.ttu.idu0080.ass1.model.ui.UIGame;
import ee.eerikmagi.ttu.idu0080.ass1.rest.json.GameResponse;
import ee.eerikmagi.ttu.idu0080.ass1.rest.json.GamesResponse;
import ee.eerikmagi.ttu.idu0080.ass1.rest.json.JsonResponse;
import ee.eerikmagi.ttu.idu0080.ass1.rest.json.SaveGameRequest;

/**
 * Resource that handles API requests
 */
@Path("games")
public class APIResource {
	private static final JsonResponse JSON_RESPONSE_FAILURE =
			new JsonResponse().setSuccess(false);
	
	@Inject
	private GamesDAO GAMES;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGames() {
		List<UIGame> games = UIGame.fromGameList(GAMES.list());
		
		return Response.ok(
			new GamesResponse()
				.setGames(games)
				.setSuccess(true)
		).build();
	}
	
	@Path("{gameID}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGame(@PathParam("gameID") long gameID) {
		Game game = GAMES.get(gameID);
		
		if (game == null) {
			return Response.serverError()
				.entity(JSON_RESPONSE_FAILURE)
				.build();
		}
		
		UIGame uiGame = UIGame.fromGame(game);
		
		return Response.ok(
			new GameResponse()
				.setGame(uiGame)
				.setSuccess(true)
		).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateGame(
		@Valid SaveGameRequest req
	) {
		UIGame g = req.getGame();
		
		if (g.getID() == null || g.getID() < 0) {
			return Response.serverError()
				.entity(JSON_RESPONSE_FAILURE)
				.build();
		}
		
		GAMES.save(g.toGame());
		
		return Response.ok(
			new JsonResponse()
				.setSuccess(true)
		).build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addGame(
		@Valid SaveGameRequest req
	) {
		UIGame g = req.getGame();

		if (g.getID() != null) {
			return Response.serverError()
					.entity(JSON_RESPONSE_FAILURE)
					.build();
		}
		
		GAMES.save(g.toGame());
		
		return Response.ok(
			new JsonResponse()
				.setSuccess(true)
		).build();
	}
	
	@Path("{gameID}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteGame(@PathParam("gameID") long gameID) {
		GAMES.delete(gameID);
		
		return Response.ok(
			new JsonResponse()
				.setSuccess(true)
		).build();
	}
	
	@Path("find/name/{query}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findGame(@PathParam("query") String query) {
		List<Game> games = GAMES.findByPartialName(query);
		List<UIGame> uiGames = UIGame.fromGameList(games);
		
		return Response.ok(
			new GamesResponse()
				.setGames(uiGames)
				.setSuccess(true)
		).build();
	}
	
	@Path("external")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listExternal() {
		Client client = ClientBuilder.newBuilder()
			.register(ObjectMapperProvider.class)
			.register(JacksonFeature.class)
			.build();
		WebTarget target = client.target("http://localhost:8085/ass1_2nd/games");
		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
		GamesResponse resp = invocationBuilder.get(GamesResponse.class);
		
		return Response.ok(
			new GamesResponse()
				.setGames(resp.getGames())
				.setSuccess(true)
		).build();
	}
}
