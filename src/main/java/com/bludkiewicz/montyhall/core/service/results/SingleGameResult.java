package com.bludkiewicz.montyhall.core.service.results;

import com.bludkiewicz.montyhall.core.service.enums.Door;
import com.bludkiewicz.montyhall.core.service.enums.Prize;

/**
 * Representation of a single games results.
 *
 * As there can be 1000s of these per request, this should follow flyweight pattern.
 */
public class SingleGameResult {

	private final Door doorWithCar;
	private final Door originalDoor;
	private final Door selectedDoor;

	public SingleGameResult(Door doorWithCar, Door originalDoor, Door selectedDoor) {
		this.doorWithCar = doorWithCar;
		this.originalDoor = originalDoor;
		this.selectedDoor = selectedDoor;
	}

	public Prize getDoorOne() {
		return getPrize(Door.ONE);
	}

	public Prize getDoorTwo() {
		return getPrize(Door.TWO);
	}

	public Prize getDoorThree() {
		return getPrize(Door.THREE);
	}

	private Prize getPrize(Door door) {
		if (doorWithCar == door) return Prize.CAR;
		return Prize.GOAT;
	}

	public Door getOriginalChoice() {
		return originalDoor;
	}

	public Door getSelectedChoice() {
		return selectedDoor;
	}

	public boolean isWinner() {
		return (selectedDoor == doorWithCar);
	}
}
