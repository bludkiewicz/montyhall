package com.bludkiewicz.montyhall.service.components;

import com.bludkiewicz.montyhall.service.enums.Door;
import com.bludkiewicz.montyhall.service.results.MultipleGameResults;
import com.bludkiewicz.montyhall.service.results.SingleGameResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResultsTrackerTest {

	@Test
	public void testBigWinner() {

		// prepare
		ResultsTracker generator = new ResultsTracker();
		generator.addResult(winner);

		MultipleGameResults results = generator.getMultipleGameResults();

		// verify wins and attempts
		assertEquals(1, results.getWins());
		assertEquals(1, results.getAttempts());

		// verify results
		List<SingleGameResult> singleResults = results.getSingleResults();
		assertEquals(winner, singleResults.get(0));
	}

	@Test
	public void testBreakEven() {

		// prepare
		ResultsTracker generator = new ResultsTracker();
		generator.addResult(winner);
		generator.addResult(loser);
		generator.addResult(winner);
		generator.addResult(loser);

		MultipleGameResults results = generator.getMultipleGameResults();

		// verify wins and attempts
		assertEquals(2, results.getWins());
		assertEquals(4, results.getAttempts());

		// verify results
		List<SingleGameResult> singleResults = results.getSingleResults();
		assertEquals(winner, singleResults.get(0));
		assertEquals(loser, singleResults.get(1));
		assertEquals(winner, singleResults.get(2));
		assertEquals(loser, singleResults.get(3));
	}

	@Test
	public void testBadNightOut() {

		// prepare
		ResultsTracker generator = new ResultsTracker();
		generator.addResult(loser);
		generator.addResult(loser);
		generator.addResult(loser);
		generator.addResult(loser);
		generator.addResult(loser);

		MultipleGameResults results = generator.getMultipleGameResults();

		// verify wins and attempts
		assertEquals(0, results.getWins());
		assertEquals(5, results.getAttempts());

		// verify results
		List<SingleGameResult> singleResults = results.getSingleResults();
		assertEquals(loser, singleResults.get(0));
		assertEquals(loser, singleResults.get(1));
		assertEquals(loser, singleResults.get(2));
		assertEquals(loser, singleResults.get(3));
		assertEquals(loser, singleResults.get(4));
	}

	private final static SingleGameResult winner = new SingleGameResult(Door.ONE, Door.TWO, Door.ONE);
	private final static SingleGameResult loser = new SingleGameResult(Door.ONE, Door.TWO, Door.THREE);
}
