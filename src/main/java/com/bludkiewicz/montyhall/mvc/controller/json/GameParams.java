package com.bludkiewicz.montyhall.mvc.controller.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Object representation of JSON input parameters.
 * Configured to integrate with Bean Validation API.
 */
public class GameParams {

	@NotNull
	@Positive
	private Integer iterations;
	@NotNull
	@JsonProperty("switch")
	private Boolean switchDoor;

	public GameParams() { }

	public GameParams(Integer iterations, Boolean switchDoor) {
		this.iterations = iterations;
		this.switchDoor = switchDoor;
	}

	public Integer getIterations() {
		return iterations;
	}

	public void setIterations(Integer iterations) {
		this.iterations = iterations;
	}

	public Boolean getSwitchDoor() {
		return switchDoor;
	}

	public void setSwitchDoor(Boolean switchDoor) {
		this.switchDoor = switchDoor;
	}

	@Override
	public String toString() {
		return "GameParams{" +
				"iterations=" + iterations +
				", switchDoor=" + switchDoor +
				'}';
	}
}
