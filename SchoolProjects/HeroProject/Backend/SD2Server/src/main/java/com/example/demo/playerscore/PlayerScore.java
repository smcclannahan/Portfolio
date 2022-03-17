package com.example.demo.playerscore;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;

import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;

/**
 * class used to keep track of a indivudal players scores
 * 
 * @author Caleb Meyer
 *
 */
@Entity
@Table(name = "playerscore", schema = "TTower")
public class PlayerScore {

	@Column(name = "level", nullable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private Integer level;

	@Id
	@Column(name = "name", length = 249, nullable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private String name;

	@Column(name = "score", nullable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private Integer score;

	/**
	 * 
	 * @return the level achieved by the player
	 */
	public Integer getLevel() {
		return level;
	}

	/**
	 * sets the level of the player
	 * 
	 * @param level
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 * 
	 * @return the name of the player
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets the name of the player
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return the score of the player
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * sets the score of the player
	 * 
	 * @param score
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

	/**
	 * sets the score of the player if higher than previous score
	 * 
	 * @param score
	 */
	public void setScoreIfHigher(Integer score) {
		if (score > this.score) {
			this.score = score;
		}
	}

	/**
	 * Returns player information as a string
	 */
	public String toString() {
		return new ToStringCreator(this).append("level", this.getLevel()).append("name", this.getName())
				.append("score", this.getScore()).toString();
	}

}
