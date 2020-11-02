package com.bludkiewicz.montyhall.core.service.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Door {

	ONE(1), TWO(2), THREE(3);

	private final Integer value;

	Door(Integer value) {
		this.value = value;
	}

	@JsonValue
	public Integer getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value.toString();
	}
}