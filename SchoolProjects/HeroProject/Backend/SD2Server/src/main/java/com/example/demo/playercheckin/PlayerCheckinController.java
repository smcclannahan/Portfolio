package com.example.demo.playercheckin;

import java.util.Optional;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class PlayerCheckinController {

	private final Logger logger = LoggerFactory.getLogger(PlayerCheckinController.class);

	@Autowired
	PlayerCheckinRepository playerCheckingRrpository;

	@RequestMapping(method = RequestMethod.PUT, path = "/setSkin")
	public String setSkin(@RequestBody String jString) {

		JSONObject playerSkin = new JSONObject(jString);
		String name = playerSkin.getString("name");
		String skin = playerSkin.getString("skin");
		logger.info("skin set attempted for " + name);
		Optional<PlayerCheckin> player = playerCheckingRrpository.findById(name);
		if (player.isPresent()) {
			PlayerCheckin playerCheckin = player.get();
			playerCheckin.setSkin(skin);
			playerCheckingRrpository.save(playerCheckin);
			logger.info("succes");
			return "succes";

		}
		logger.info("fail");
		return "Fail";

	}

	@RequestMapping(method = RequestMethod.GET, path = "getSkin/{name}")
	public String getSkin(@PathVariable("name") String name) {

		Optional<PlayerCheckin> player = playerCheckingRrpository.findById(name);
		logger.info("retriving skin for" + name);
		if (player.isPresent()) {
			PlayerCheckin playerCheckin = player.get();
			logger.info("success");
			return playerCheckin.getSkin();
		}

		logger.info("fial");
		return null;

	}

}
