package com.bludkiewicz.montyhall.service.state;

import com.bludkiewicz.montyhall.service.enums.Door;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GameDoorsTest {

	@Test
	public void testDoors() {

		// at the moment all games use the same doors
		List<Door> allDoors = new GameDoors().getPossibleDoorOptions();

		// it's really hard to test for randomness
		// so we're going to run through these tests enough times
		// so that each door should have a car behind it at least once
		Set<Door> carDoors = new HashSet<>(allDoors);
		for (int i = 0; i < 100; i++) {

			// prepare
			GameDoors gameDoors = new GameDoors();

			// validate door with car is a possible option
			Door doorWithCar = gameDoors.getDoorWithCar();
			assertTrue(allDoors.contains(doorWithCar));

			// for all possible original door selections
			allDoors.forEach(originalDoor -> {
				// ask to have a door with a goat opened
				Door openedDoor = gameDoors.getOpenedDoor(originalDoor);
				// verify the door opened is not the one with a car
				assertNotEquals(doorWithCar, openedDoor);
				// verify the door opened is a possible option
				assertTrue(allDoors.contains(openedDoor));
			});

			// keep track of which door had the car
			if(!carDoors.isEmpty()) carDoors.remove(doorWithCar);
		}

		// verify all possible doors had a car behind them at least once
		assertEquals(0, carDoors.size());
	}
}
