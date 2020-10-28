package com.bludkiewicz.montyhall.service.params;

import java.util.Objects;

/**
 * Convenience class that contains various game options.
 */
public class GameOptions {

	private boolean switchDoors;

	public GameOptions() { }

	public GameOptions(boolean switchDoors) {
		this.switchDoors = switchDoors;
	}

	public boolean isSwitchDoors() {
		return switchDoors;
	}

	public void setSwitchDoors(boolean switchDoors) {
		this.switchDoors = switchDoors;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GameOptions that = (GameOptions) o;
		return switchDoors == that.switchDoors;
	}

	@Override
	public int hashCode() {
		return Objects.hash(switchDoors);
	}

	@Override
	public String toString() {
		return "GameOptions{" +
				"switchDoors=" + switchDoors +
				'}';
	}
}
