package com.example.demo.coop;

import java.util.List;
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

/**
 * 
 * @author Caleb L. Meyer This class is the controller for coop (co-op)
 *
 */
@RestController
public class CoopController {

	@Autowired
	CoopRepository coopRepository;

	private final Logger logger = LoggerFactory.getLogger(CoopController.class);

	/**
	 * Accessed by endpoint /coopScore with GET request
	 * 
	 * @return a list of all coop elements stored in the database
	 * 
	 */

	@RequestMapping(method = RequestMethod.GET, path = "/coopScore")
	public List<Coop> getallcoopscore() {
		logger.info("all coop fetched");
		List<Coop> allCoop = coopRepository.findAll();
		return allCoop;
	}

	/**
	 * Access by endpoint /coopScore with POST request
	 * 
	 * @param Coopname the combined name of the players with a space in the middle
	 * @param score    score of the players
	 * @param level    level that the players reached Tries to create a new coop
	 *                 element in the database unless an element with the same name
	 *                 already exists returns a string indicating success or failure
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/coopScore")
	public String addcoop(@RequestBody String jString) {

		JSONObject jObject = new JSONObject(jString);

		String Coopname = jObject.getString("coopname");
		int score = jObject.getInt("score");
		int level = jObject.getInt("level");
		if (coopRepository.findById(Coopname).isPresent()) {
			return "already Exist";
		}
		Coop newCoop = new Coop();
		newCoop.setCombined(Coopname);
		newCoop.setScore(score);
		newCoop.setLevel(level);
		coopRepository.save(newCoop);

		return "succes";
	}

	/**
	 * Access by endpoint /coopScore with PUT request
	 * 
	 * @param Coopname the combined name of the players with a space in the middle
	 * @param score    score of the players
	 * @param level    level that the players reached tries to update an existing
	 *                 element in the database unless that elements dosn't exist
	 *                 returns a string indicating success or failure
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/coopScore")
	public String updatecoop(@RequestBody String jString) {

		JSONObject jObject = new JSONObject(jString);

		String Coopname = jObject.getString("coopname");
		int score = jObject.getInt("score");
		int level = jObject.getInt("level");

		Optional<Coop> oldCoop = coopRepository.findById(Coopname);
		if (oldCoop.isPresent()) {
			Coop updateCoop = oldCoop.get();
			updateCoop.setScore(score);
			updateCoop.setLevel(level);
			coopRepository.save(updateCoop);
			return "updated";
		}

		return "no Such Element";
	}

	/**
	 * Accessed by endpoint /score/{playername}
	 * 
	 * @param name name of the player
	 * @return a list containing a list were playername is one of the players
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/coopScore/{playername}")
	public List<Coop> getSpecificPlayer(@PathVariable("playername") String name) {
		List<Coop> asP1 = coopRepository.findByPlayer1(name);
		List<Coop> asP2 = coopRepository.findByPlayer2(name);
		asP1.addAll(asP2);

		return asP1;

	}

}
