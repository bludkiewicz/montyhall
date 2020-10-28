package com.bludkiewicz.montyhall.service.components;

import com.bludkiewicz.montyhall.service.enums.Door;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Bean that contains game state.
 *
 * Each new simulation should receive a fresh copy of this.
 */
@Component
@Scope("prototype")
public class GameDoors {

	private final List<Door> doors;

	public GameDoors() {

		doors = getPossibleDoorOptions();

		// randomly shuffles the order of those doors a few times
		for (int i = 0; i < 3; i++)
			Collections.shuffle(doors);
	}

	/**
	 * Returns which door the car was behind.
	 */
	public Door getDoorWithCar() {

		// the first door in our random list is always the car
		return doors.get(0);
	}

	/**
	 * Returns the 'opened door'.
	 * Needs to know what the selected door was so it's not returned.
	 */
	public Door getOpenedDoor(Door selectedDoor) {

		// if the 0 element is always the car
		// that means the 1 and 2 elements are the goats
		// we need to know which door was selected so we don't return that one
		Door opened = doors.get(1);
		if (opened == selectedDoor) opened = doors.get(2);

		return opened;
	}

	/**
	 * Get possible door options.
	 */
	public List<Door> getPossibleDoorOptions() {

		// always need to return a new instance
		return Arrays.asList(Door.values());
	}
}
