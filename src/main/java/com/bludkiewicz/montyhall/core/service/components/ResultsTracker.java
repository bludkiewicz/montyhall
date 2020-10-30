package com.bludkiewicz.montyhall.core.service.components;

import com.bludkiewicz.montyhall.core.service.results.MultipleGameResults;
import com.bludkiewicz.montyhall.core.service.results.SingleGameResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Stateful bean that keeps track of results over multiple iterations.
 */
@Component
@Scope("prototype")
public class ResultsTracker {

	// using linked list to avoid array resizing
	// since results are always added at tail
	private final List<SingleGameResult> results = new LinkedList<>();
	private int wins;
	private int attempts;

	public ResultsTracker() { }

	public void addResult(SingleGameResult result) {

		results.add(result);

		attempts++;
		if (result.isWinner()) wins++;
	}

	public MultipleGameResults getMultipleGameResults() {
		return new MultipleGameResults(wins, attempts, new ArrayList<>(results));
	}
}
