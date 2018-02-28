package ee.eerikmagi.ttu.idu0080.ass1.model.db;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="game")
public class Game {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long lID;
	
	@Column(name="name")
	@NotNull
	private String sName;
	
	@Column(name="release_date")
	@NotNull
	private LocalDate sReleaseDate;
	
	@Column(name="developer")
	private String sDeveloper;
	
	@Column(name="score")
	@Min(value=1)
	@Max(value=10)
	private Integer nScore;

	public Long getID() {
		return lID;
	}

	public void setID(Long lID) {
		this.lID = lID;
	}

	public String getName() {
		return sName;
	}

	public void setName(String sName) {
		this.sName = sName;
	}

	public LocalDate getReleaseDate() {
		return sReleaseDate;
	}

	public void setReleaseDate(LocalDate sReleaseDate) {
		this.sReleaseDate = sReleaseDate;
	}

	public String getDeveloper() {
		return sDeveloper;
	}

	public void setDeveloper(String sDeveloper) {
		this.sDeveloper = sDeveloper;
	}

	public Integer getScore() {
		return nScore;
	}

	public void setScore(Integer nScore) {
		this.nScore = nScore;
	}
}
