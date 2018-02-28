package ee.eerikmagi.ttu.idu0080.ass1.rest.json;

import ee.eerikmagi.ttu.idu0080.ass1.model.ui.UIGame;

public class GameResponse extends JsonResponse {
	private UIGame game;

	public UIGame getGame() {
		return game;
	}
	public GameResponse setGame(UIGame game) {
		this.game = game;
		return this;
	}
}
