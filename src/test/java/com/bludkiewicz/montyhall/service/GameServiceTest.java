package com.bludkiewicz.montyhall.service;

import com.bludkiewicz.montyhall.service.enums.Door;
import com.bludkiewicz.montyhall.service.results.MultipleGameResults;
import com.bludkiewicz.montyhall.service.state.GameDoors;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTest {

	@Test
	public void testSwitchedDoorsAndRandomness() {

		GameService service = new GameService();

		int iterations = 100;
		MultipleGameResults results = service.simulate(iterations, true);

		// verify correct number of iterations
		assertEquals(iterations, results.getAttempts());

		// once again, it's really hard to test for randomness
		Set<Door> originalSelections = new HashSet<>(new GameDoors().getPossibleDoorOptions());
		results.getSingleResults().forEach(result -> {
			// verify door was switched
			assertNotSame(result.getOriginalChoice(), result.getSelectedChoice());
			if(!originalSelections.isEmpty()) originalSelections.remove(result.getOriginalChoice());
		});

		// randomness check
		assertEquals(0, originalSelections.size());
	}

	@Test
	public void testSameDoors() {

		GameService service = new GameService();

		int iterations = 100;
		MultipleGameResults results = service.simulate(iterations, false);

		// verify correct number of iterations
		assertEquals(iterations, results.getAttempts());

		results.getSingleResults().forEach(result ->
			// verify door was not switched
			assertSame(result.getOriginalChoice(), result.getSelectedChoice())
		);
	}
}
