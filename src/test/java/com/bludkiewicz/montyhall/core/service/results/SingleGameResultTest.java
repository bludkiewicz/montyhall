package com.bludkiewicz.montyhall.core.service.results;

import com.bludkiewicz.montyhall.core.service.enums.Door;
import com.bludkiewicz.montyhall.core.service.enums.Prize;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SingleGameResultTest {

	@Test
	public void testDoorOneNoWinner() {

		Door doorWithCar = Door.ONE; // door one had the car
		Door originalDoor = Door.ONE; // door one was originally selected
		Door selectedDoor = Door.TWO; // selection changed to door two

		// prepare
		SingleGameResult result = new SingleGameResult(doorWithCar, originalDoor, selectedDoor);

		// verify door one had the car and the rest were goats
		assertEquals(Prize.CAR, result.getDoorOne());
		assertEquals(Prize.GOAT, result.getDoorTwo());
		assertEquals(Prize.GOAT, result.getDoorThree());

		// verify the originalDoor and selectedDoor are correct
		assertEquals(Door.ONE, result.getOriginalChoice());
		assertEquals(Door.TWO, result.getSelectedChoice());

		// verify that we did not win
		// (original door == correct door) != win
		assertFalse(result.isWinner());
	}

	@Test
	public void testDoorTwoNoWinner() {

		Door doorWithCar = Door.TWO; // door two had the car
		Door originalDoor = Door.ONE; // door one was originally selected
		Door selectedDoor = Door.ONE; // selection was not changed

		// prepare
		SingleGameResult result = new SingleGameResult(doorWithCar, originalDoor, selectedDoor);

		// verify door two had the car and the rest were goats
		assertEquals(Prize.GOAT, result.getDoorOne());
		assertEquals(Prize.CAR, result.getDoorTwo());
		assertEquals(Prize.GOAT, result.getDoorThree());

		// verify the originalDoor and selectedDoor are correct
		assertEquals(Door.ONE, result.getOriginalChoice());
		assertEquals(Door.ONE, result.getSelectedChoice());

		// verify that we did not win
		// (original door == selected door ) != win
		assertFalse(result.isWinner());
	}

	@Test
	public void testDoorThreeWinner() {

		Door doorWithCar = Door.THREE; // door three had the car
		Door originalDoor = Door.TWO; // door two was originally selected
		Door selectedDoor = Door.THREE; // selection was changed to door three

		// prepare
		SingleGameResult result = new SingleGameResult(doorWithCar, originalDoor, selectedDoor);

		// verify door three had the car and the rest were goats
		assertEquals(Prize.GOAT, result.getDoorOne());
		assertEquals(Prize.GOAT, result.getDoorTwo());
		assertEquals(Prize.CAR, result.getDoorThree());

		// verify the originalDoor and selectedDoor are correct
		assertEquals(Door.TWO, result.getOriginalChoice());
		assertEquals(Door.THREE, result.getSelectedChoice());

		// verify that we did win
		// (selected door == correct door ) = win
		assertTrue(result.isWinner());
	}
}
