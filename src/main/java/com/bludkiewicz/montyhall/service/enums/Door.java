package com.bludkiewicz.montyhall.service.enums;

public enum Door {

	ONE(1), TWO(2), THREE(3);

	private final Integer value;

	Door(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value.toString();
	}
}