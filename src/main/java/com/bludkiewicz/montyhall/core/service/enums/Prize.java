package com.bludkiewicz.montyhall.core.service.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Prize {

	CAR("\uD83D\uDE97"), GOAT("\uD83D\uDC10");

	private final String value;

	Prize(String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value;
	}
}
