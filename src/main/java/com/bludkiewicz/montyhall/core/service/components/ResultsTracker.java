package com.bludkiewicz.montyhall.core.service.components;

import com.bludkiewicz.montyhall.core.service.results.MultipleGameResults;
import com.bludkiewicz.montyhall.core.service.results.SingleGameResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Stateful bean that keeps track of results over multiple iterations.
 * Generates final results object.
 */
@Component
@Scope("prototype")
public class ResultsTracker {

	// using linked list to avoid array resizing
	// also results are always added at tail and there is no index based retrieval
	private final List<SingleGameResult> results = new LinkedList<>();
	private int wins;

	@Value("${output.max_results_to_show:1000}")
	private int maxResults;

	public ResultsTracker() {
	}

	public void addResult(SingleGameResult result) {

		results.add(result);
		if (result.isWinner()) wins++;
	}

	public MultipleGameResults getMultipleGameResults() {

		List<SingleGameResult> singleResults;
		if (results.size() <= maxResults) singleResults = new ArrayList<>(results);
		else singleResults = Collections.emptyList();

		return new MultipleGameResults(wins, results.size(), singleResults);
	}
}
