package ee.eerikmagi.ttu.idu0080.ass1.rest.json;

import java.util.List;

import ee.eerikmagi.ttu.idu0080.ass1.model.ui.UIGame;

public class GamesResponse extends JsonResponse {
	private List<UIGame> games;

	public List<UIGame> getGames() {
		return games;
	}
	public GamesResponse setGames(List<UIGame> games) {
		this.games = games;
		return this;
	}
}
