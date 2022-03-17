package com.example.demo.playerscore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.example.demo.playerscore.PlayerScore;
import com.example.demo.playerscore.PlayerScoreRepository;

/**
 * 
 * @author Caleb L. Meyer this class is used to keep track of individual player
 *         scores
 *
 */
@RestController
public class PlayerScoreController {

	@Autowired
	PlayerScoreRepository scoreRepository;

	private final Logger logger = LoggerFactory.getLogger(PlayerScoreController.class);

	/**
	 * accessed by endpoint /score with GET request
	 * 
	 * @return a list of all player scores
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/score")
	public List<PlayerScore> getallPlayerScore() {
		logger.info("Entered into Controller Layer");
		List<PlayerScore> results = scoreRepository.findAll();
		logger.info("Number of Records Fethced:" + results.size());
		return results;
	}

	/**
	 * accessed by endpoint /score with POST request creates a new player score in
	 * the database
	 * 
	 * @param player player level name and score in JSON format
	 * @return indicating success or failure
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/score")
	public String addScore(@RequestBody PlayerScore player) {

		logger.info("attempeted addition to database");
		if (scoreRepository.findByName(player.getName()).isPresent()) {
			logger.info("attempred fail element already esits.");
			return "Element with id " + player.getName().toString() + " alreayd esits";

		}
		logger.info("attemp succes");
		scoreRepository.save(player);
		return player.getName() + "saved";

	}

	/**
	 * accessed by endpoint /score with PUT request updates an already existing
	 * score
	 * 
	 * @param player Player level name and score in JSON format
	 * @return a message indicating succes or failure
	 */
	@RequestMapping(method = RequestMethod.PUT, path = "/score")
	public String updateScore(@RequestBody PlayerScore player) {

		logger.info("update attempt");

		if (!scoreRepository.findById(player.getName()).isPresent()) {
			logger.info("attempt failed no such element");
			return "no such element";
		}

		scoreRepository.save(player);
		logger.info("score updated");

		return "updated";

	}

}
