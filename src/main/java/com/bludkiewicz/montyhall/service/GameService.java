package com.bludkiewicz.montyhall.service;

import com.bludkiewicz.montyhall.service.enums.Door;
import com.bludkiewicz.montyhall.service.results.MultipleGameResults;
import com.bludkiewicz.montyhall.service.results.SingleGameResult;
import com.bludkiewicz.montyhall.service.state.GameDoors;
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

	/**
	 * Simulates multiple games.
	 */
	public MultipleGameResults simulate(int iterations, boolean switchDoor) {

		log.info("Simulating {} games {} switching doors", iterations, (switchDoor ? "while" : "without"));

		MultipleGameResults results = new MultipleGameResults();
		for (int i = 1; i <= iterations; i++) {

			log.debug("Game number: {}", i);

			SingleGameResult result = simulate(switchDoor);
			results.addResult(result);
		}

		log.debug("Final results: {}", results);
		return results;
	}

	/**
	 * Simulates a single game.
	 */
	private SingleGameResult simulate(boolean switchDoor) {

		GameDoors game = new GameDoors();
		List<Door> allDoors = game.getPossibleDoorOptions();

		// pick a door, any door...
		int index = new Random().nextInt(allDoors.size());
		Door originalDoor = allDoors.get(index);
		log.debug("Original door: {}", originalDoor);

		Door selectedDoor;
		if (!switchDoor) {

			// if we are not switching doors
			// then the rest of the game always plays out the same in the end
			// no need to run through unnecessary processing
			selectedDoor = originalDoor;

		} else {

			// open a door to reveal a goat
			Door openedDoor = game.getOpenedDoor(originalDoor);
			log.debug("Opened door: {}", openedDoor);

			// remove the original door and the opened door from possible options
			Set<Door> options = new HashSet<>(allDoors);
			options.remove(originalDoor);
			options.remove(openedDoor);

			// there should only be one door left, which we select
			selectedDoor = options.iterator().next();
		}

		log.debug("Selected door: {}", selectedDoor);

		Door correctDoor = game.getDoorWithCar();
		log.debug("Correct door: {}", correctDoor);

		SingleGameResult result = new SingleGameResult(correctDoor, originalDoor, selectedDoor);
		log.debug("Winner? {}", result.isWinner());

		return result;
	}
}
