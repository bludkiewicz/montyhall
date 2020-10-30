package com.bludkiewicz.montyhall.core.service.enums;

public enum Door {

	ONE(1), TWO(2), THREE(3);

	private final Integer value;

	Door(Integer value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value.toString();
	}
}