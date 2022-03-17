package com.example.demo.WebSocket.ahost;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.playerlogin.PlayerLogin;
import com.example.demo.playerlogin.PlayerLoginRepository;

@RestController
public class AhostController {

	@Autowired
	AhostRepository ahostRepository;

	@Autowired
	PlayerLoginRepository playerLoginRepositry;

	private final Logger logger = LoggerFactory.getLogger(AhostController.class);

	@RequestMapping(method = RequestMethod.PUT, path = "/hostgame")
	public int hostGame(@RequestBody String jString) {
		JSONObject ahost = new JSONObject(jString);
		String hostname = ahost.getString("hostname");
		logger.info(hostname);
		Optional<PlayerLogin> owner = playerLoginRepositry.findById(hostname);
		if (ahostRepository.findByHostname(hostname).isPresent()) {
			return -1;
		}
		if (owner.isPresent()) {
			Ahost host = new Ahost();
			host.setHostname(hostname);

			owner.get().getHosts().add(host);
			playerLoginRepositry.save(owner.get());
			// ah.save(host);

			return ahostRepository.findByHostname(hostname).get().getId();
		}

		return -1;
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/joingame")
	public List<Ahost> joinGame(@RequestBody String jString) {
		
		
		return ahostRepository.findAll();
	}

}
