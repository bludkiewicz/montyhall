package com.bludkiewicz.montyhall.service.enums;

public enum Prize {

	CAR("\uD83C\uDFCE"), GOAT("\uD83D\uDC10");

	private final String value;

	Prize(String value ) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value;
	}
}
