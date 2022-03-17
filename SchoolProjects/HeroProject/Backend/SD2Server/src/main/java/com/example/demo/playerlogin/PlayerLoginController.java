package com.example.demo.playerlogin;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import com.example.demo.playerlogin.PlayerLoginRepository;
import com.example.demo.playerlogin.PlayerLogin;

import com.example.demo.playercheckin.*;

/**
 * 
 * @author Caleb L Meyer class used to log in players
 *
 */

@RestController
public class PlayerLoginController {

	@Autowired
	PlayerLoginRepository playerLoginRepositry;

	@Autowired
	PlayerCheckinRepository playerCheckinRepository;

	private final Logger logger = LoggerFactory.getLogger(PlayerLoginController.class);

	/**
	 * Accessed by end point /login with GET request logs in a player
	 * 
	 * @param l players name and password as JSON format
	 * @return message indicating success or failure
	 * 
	 */

	@RequestMapping(method = RequestMethod.PUT, path = "/login")
	public String login(@RequestBody String jString) {
		JSONObject login = new JSONObject(jString);
		String name = login.getString("name");
		String password = login.getString("password");

		logger.info("attmepted login");
		Optional<PlayerLogin> player = playerLoginRepositry.findByName(name);
		if (!player.isPresent()) {
			logger.info("attemp faild");
			return "no such user";
		}

		PlayerLogin playerLogin = player.get();

		if (!playerLogin.getPassword().equals(password)) {
			logger.info("attampt faild");
			return "wrong password";
		}

		logger.info("attempt succes");

		playerLogin.getPlayerCheckin().setIsLoggedIn(true);
		PlayerCheckin check = playerCheckinRepository.findById(name).get();
		check.setIsLoggedIn(true);
		playerLoginRepositry.save(playerLogin);

		return name + " logged in.";
	}

	/**
	 * accessed by endpoint /logout with GET request logs out a player
	 * 
	 * @param name player name to be logged out
	 * @return a message indicating success
	 */

	@RequestMapping(method = RequestMethod.GET, path = "/logout")
	public String logout(@RequestBody String name) {
		Optional<PlayerLogin> player = playerLoginRepositry.findByName(name);
		PlayerLogin playerLogin = player.get();
		playerLogin.getPlayerCheckin().setIsLoggedIn(false);
		PlayerCheckin check = playerCheckinRepository.findById(name).get();
		check.setIsLoggedIn(false);

		playerLoginRepositry.save(playerLogin);

		return name + "louged out";
	}

	/**
	 * accessed by endpoint /newPlayer with POST request adds a new player to the
	 * database if name is not alreay taken
	 * 
	 * @param l players name and password as JSON format
	 * @return a message indicating success or failure
	 */

	@RequestMapping(method = RequestMethod.POST, path = "/newPlayer")
	public String newPlayer(@RequestBody String jString) {

		JSONObject login = new JSONObject(jString);
		String name = login.getString("name");
		String password = login.getString("password");
		logger.info(name);

		logger.info("new user attempted");
		Optional<PlayerLogin> player = playerLoginRepositry.findByName(name);
		if (player.isPresent()) {
			logger.info("user already existed");
			return "That user name is alrady taken";
		}

		PlayerCheckin newCheckin = new PlayerCheckin();
		newCheckin.setName(name);
		PlayerLogin l = new PlayerLogin();
		l.setName(name);
		l.setPassword(password);
		newCheckin.setSkin("p1");
		newCheckin.setPlayerLogin(l);
		l.setPlayerCheckin(newCheckin);

		// logger.info(l.getName()+" "+newCheckin.getName());
		logger.info("here");
		playerLoginRepositry.save(l);
		playerCheckinRepository.save(newCheckin);

		return "welcome";
	}

	/**
	 * Deletes a player
	 * 
	 * @param l players name and password as JSON format
	 * @return a message indicating success
	 */
	@RequestMapping(method = RequestMethod.DELETE, path = "/removePlayer")
	public String removePlayer(@RequestBody String name, @RequestBody String Password) {

		logger.info("player removal intiated");
		playerLoginRepositry.deleteById(name);

		return name + "removed";
	}

}
