package com.bludkiewicz.montyhall.service.enums;

public enum Prize {

	CAR("\uD83D\uDE97"), GOAT("\uD83D\uDC10");

	private final String value;

	Prize(String value ) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
