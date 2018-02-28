package ee.eerikmagi.ttu.idu0080.ass1.rest.json;

import javax.validation.constraints.NotNull;

import ee.eerikmagi.ttu.idu0080.ass1.model.ui.UIGame;

public class SaveGameRequest {
	@NotNull
	private UIGame game;

	public UIGame getGame() {
		return game;
	}
	public SaveGameRequest setGame(UIGame game) {
		this.game = game;
		return this;
	}
}
