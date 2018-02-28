package ee.eerikmagi.ttu.idu0080.ass1.model.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import ee.eerikmagi.ttu.idu0080.ass1.model.db.Game;

public class UIGame {
	private Long id;
	
	@NotNull
	private String name;
	
	@NotNull
	private LocalDate releaseDate;
	
	private String developer;
	
	private Integer score;

	@Min(value=1)
	@Max(value=10)
	public Long getID() {
		return id;
	}

	public UIGame setID(Long lID) {
		this.id = lID;
		return this;
	}

	public String getName() {
		return name;
	}

	public UIGame setName(String sName) {
		this.name = sName;
		return this;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public UIGame setReleaseDate(LocalDate sReleaseDate) {
		this.releaseDate = sReleaseDate;
		return this;
	}

	public String getDeveloper() {
		return developer;
	}

	public UIGame setDeveloper(String sDeveloper) {
		this.developer = sDeveloper;
		return this;
	}

	public Integer getScore() {
		return score;
	}

	public UIGame setScore(Integer nScore) {
		this.score = nScore;
		return this;
	}
	
	public static UIGame fromGame(Game game) {
		return new UIGame()
			.setID(game.getID())
			.setName(game.getName())
			.setReleaseDate(game.getReleaseDate())
			.setDeveloper(game.getDeveloper())
			.setScore(game.getScore());
	}
	
	public Game toGame() {
		Game g = new Game();
		
		g.setID(this.getID());
		g.setDeveloper(this.getDeveloper());
		g.setName(this.getName());
		g.setReleaseDate(this.getReleaseDate());
		g.setScore(this.getScore());
		
		return g;
	}
	
	public static List<UIGame> fromGameList(List<Game> games) {
		return games.stream().map(UIGame::fromGame).collect(Collectors.toList());
	}
}
