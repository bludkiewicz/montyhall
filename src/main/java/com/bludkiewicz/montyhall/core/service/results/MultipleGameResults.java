package com.bludkiewicz.montyhall.core.service.results;

import java.util.List;

/**
 * Representation of multiple game results.
 */
public class MultipleGameResults {

	private final int wins;
	private final int attempts;
	private final List<SingleGameResult> results;

	public MultipleGameResults(int wins, int attempts, List<SingleGameResult> results) {
		this.wins = wins;
		this.attempts = attempts;
		this.results = results;
	}

	public int getWins() {
		return wins;
	}

	public int getAttempts() {
		return attempts;
	}

	public List<SingleGameResult> getSingleResults() {
		return results;
	}
}
