package com.bludkiewicz.montyhall.service.results;

import java.util.LinkedList;
import java.util.List;

/**
 * Representation of multiple game results.
 */
public class MultipleGameResults {

	// using a linked list to avoid ArrayList resizing
	private final List<SingleGameResult> results = new LinkedList<>();
	private int wins;
	private int attempts;

	public MultipleGameResults() {
	}

	public void addResult(SingleGameResult result) {

		results.add(result);

		attempts++;
		if (result.isWinner()) wins++;
	}

	public List<SingleGameResult> getSingleResults() {

		return results;
	}

	public int getAttempts() {

		return attempts;
	}

	public double getWinPercentage() {
		return ((double) wins / attempts) * 100;
	}

	@Override
	public String toString() {
		return "MultipleGameResults{" +
				"iterations=" + results.size() +
				", Win Percentage=" + getWinPercentage() +
				'}';
	}
}
