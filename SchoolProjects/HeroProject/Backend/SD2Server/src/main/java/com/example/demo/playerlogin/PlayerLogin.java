package com.example.demo.playerlogin;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;

import com.example.demo.WebSocket.ahost.Ahost;
import com.example.demo.playercheckin.PlayerCheckin;

/**
 * 
 * @author Caleb L mayer class used to keep track of usernames and passwords
 *
 */
@Entity
@Table(name = "playerlogin", schema = "TTower")
public class PlayerLogin {

	@Id
	@Column(name = "name", length = 249, nullable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private String name;

	@Column(name = "password", nullable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private String password;

	@OneToOne(mappedBy = "PlayerLogin", cascade = CascadeType.ALL)
	@JoinColumn(name = "playercheckin")
	private PlayerCheckin PlayerCheckin;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "ahost")
	private List<Ahost> hosts = new ArrayList<>();

	/**
	 *
	 * @return the name of player
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets the name of the player
	 * 
	 * @param playername
	 */
	public void setName(String playername) {
		this.name = playername;
	}

	/**
	 * 
	 * @return the password of the player
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * sets the password for this player
	 * 
	 * @param playerpassword
	 */
	public void setPassword(String playerpassword) {
		this.password = playerpassword;
	}

	public void setPlayerCheckin(PlayerCheckin PlayerCheckin) {
		this.PlayerCheckin = PlayerCheckin;
	}

	public PlayerCheckin getPlayerCheckin() {
		return PlayerCheckin;
	}

	public List<Ahost> getHosts() {
		return hosts;
	}

	public String toString() {
		return new ToStringCreator(this).append("name", this.getName()).append("password", this.getPassword())
				.toString();
	}

}
