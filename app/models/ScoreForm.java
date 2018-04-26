package models;

import play.data.validation.Constraints.Required;

public class ScoreForm {

	@Required
	private Long score;

	/**
	 * Returns the score
	 * 
	 * @return Long score
	 */
	public Long getScore() {
		return score;
	}

	/**
	 * Sets the score
	 * 
	 * @param Long score
	 */
	public void setScore(Long score) {
		this.score = score;
	}

}