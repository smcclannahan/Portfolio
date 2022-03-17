package com.example.demo.playercheckin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.example.demo.playerlogin.PlayerLogin;

/**
 * 
 * @author Caleb Meyer class used to keeep track of if a player is logged in
 */
@Entity
@Table(name = "playercheckin", schema = "TTower")
public class PlayerCheckin {

	@Id
	@Column(name = "name", length = 249, nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private String name;

	@Column(name = "isLoggedIn", nullable = false, columnDefinition = "boolean default false")
	@NotFound(action = NotFoundAction.IGNORE)
	private boolean isLoggedIn;

	@Column(name = "skin", nullable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private String skin;

	@OneToOne
	@MapsId
	private PlayerLogin PlayerLogin;

	/**
	 * 
	 * @return name of player
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
	 * @return a boolean that is true if the player is logged in and false otherwise
	 */
	public boolean getIsLoggedIn() {
		return isLoggedIn;
	}

	/**
	 * sets sets weather the player is logged in or not
	 * 
	 * @param isLoggedIn
	 */
	public void setIsLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public PlayerLogin getPlayerLogin() {
		return PlayerLogin;
	}

	public void setPlayerLogin(PlayerLogin PlayerLogin) {
		this.PlayerLogin = PlayerLogin;
	}

}
