package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "high_score")
public class HighScores {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "User", nullable = false, length = 20)
	private String username;

	@Column(name = "Score", nullable = true)
	private Long highScore;

	/**
	 * Returns the high score
	 * 
	 * @return Long highScore
	 */
	public Long getHighScore() {
		return highScore;
	}

	/**
	 * Sets the high score
	 * 
	 * @param highScore
	 */
	public void setHighScore(Long highScore) {
		this.highScore = highScore;
	}

	/**
	 * Returns the long value of id
	 * 
	 * @return Long id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the long value of id
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the private String username
	 * 
	 * @return String username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * void setUsername takes a string argument and sets the private variable
	 * "username" to the value of the passed in string
	 * 
	 * @param String
	 *            username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}