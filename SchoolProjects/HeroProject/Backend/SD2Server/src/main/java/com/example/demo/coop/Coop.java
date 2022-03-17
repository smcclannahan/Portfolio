package com.example.demo.coop;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * 
 * @author Caleb L Meyer class used to create and store scores for co-op
 *
 */
@Entity
@Table(name = "Coop", schema = "TTower")
public class Coop {

	@Id
	@Column(name = "combinedname", length = 249, nullable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private String combinedname;

	@Column(name = "score", nullable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private int score;

	@Column(name = "player1", nullable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private String player1;

	@Column(name = "player2", nullable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private String player2;

	@Column(name = "level", nullable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private int level;

	/**
	 * 
	 * @return The combined name of the two players
	 */
	public String getCombined() {
		return combinedname;
	}

	/**
	 * sets the combined name for the class also sets the name for player1 and
	 * player2
	 * 
	 * @param combinedname
	 */
	public void setCombined(String combinedname) {
		this.combinedname = combinedname;
		String nameSplit[] = this.combinedname.split(" ");
		player1 = nameSplit[0];
		player2 = nameSplit[1];
	}

	/**
	 * 
	 * @return the name of player1
	 */
	public String getPlayer1() {
		return player1;
	}

	/**
	 * 
	 * @return the name of player 2
	 */
	public String getPlayer2() {
		return player2;
	}

	/**
	 * 
	 * @return the score of the players
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Sets the score of the players
	 * 
	 * @param score
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * gets the highest level achieved
	 * 
	 * @return
	 */

	public int getLevel() {
		return level;
	}

	/**
	 * sets the level
	 * 
	 * @param level
	 */
	public void setLevel(int level) {
		this.level = level;
	}

}
