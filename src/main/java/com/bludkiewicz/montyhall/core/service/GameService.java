package com.bludkiewicz.montyhall.core.service;

import com.bludkiewicz.montyhall.core.service.components.ComponentFactory;
import com.bludkiewicz.montyhall.core.service.components.GameDoors;
import com.bludkiewicz.montyhall.core.service.components.ResultsTracker;
import com.bludkiewicz.montyhall.core.service.enums.Door;
import com.bludkiewicz.montyhall.core.service.params.GameOptions;
import com.bludkiewicz.montyhall.core.service.results.MultipleGameResults;
import com.bludkiewicz.montyhall.core.service.results.SingleGameResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class GameService {

	private static final Logger log = LoggerFactory.getLogger(GameService.class);

	private final ComponentFactory components;

	public GameService(ComponentFactory components) {
		this.components = components;
	}

	/**
	 * Simulates multiple games.
	 */
	public MultipleGameResults simulate(int iterations, GameOptions options) {

		log.info("Simulating {} games {} switching doors", iterations, (options.isSwitchDoors() ? "while" : "without"));

		ResultsTracker tracker = components.getResultsTracker();
		for (int i = 1; i <= iterations; i++) {

			log.debug("Game number: {}", i);
			tracker.addResult(simulate(options));
		}

		MultipleGameResults results = tracker.getMultipleGameResults();
		log.debug("Overall results: {}", results);

		return results;
	}

	/**
	 * Simulates a single game.
	 */
	private SingleGameResult simulate(GameOptions options) {

		GameDoors game = components.getGameDoors();

		// pick a door, any door...
		List<Door> allDoors = game.getPossibleDoorOptions();
		int index = new Random().nextInt(allDoors.size());
		Door originalDoor = allDoors.get(index);
		log.debug("Original door: {}", originalDoor);

		Door selectedDoor;
		if (!options.isSwitchDoors()) {

			// if we are not switching doors
			// then the end result will always be the same
			// no need to run through unnecessary processing
			selectedDoor = originalDoor;

		} else {

			// open a door to reveal a goat
			Door openedDoor = game.getOpenedDoor(originalDoor);
			log.debug("Opened door: {}", openedDoor);

			// remove the original door and the opened door from possible options
			Set<Door> doorOptions = new HashSet<>(allDoors);
			doorOptions.remove(originalDoor);
			doorOptions.remove(openedDoor);

			// there should only be one door left, which we select
			selectedDoor = doorOptions.iterator().next();
		}

		log.debug("Selected door: {}", selectedDoor);

		Door correctDoor = game.getDoorWithCar();
		log.debug("Correct door: {}", correctDoor);

		SingleGameResult result = new SingleGameResult(correctDoor, originalDoor, selectedDoor);
		log.debug("Winner? {}", result.isWinner());

		return result;
	}
}
