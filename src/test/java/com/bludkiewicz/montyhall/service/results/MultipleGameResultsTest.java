package com.bludkiewicz.montyhall.service.results;

import com.bludkiewicz.montyhall.service.enums.Door;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultipleGameResultsTest {

	@Test
	public void testBigWinner() {

		// prepare
		MultipleGameResults results = new MultipleGameResults();
		results.addResult(winner);

		// verify win percentage
		assertEquals(100d, results.getWinPercentage());

		// verify results
		assertEquals(1, results.getAttempts());

		List<SingleGameResult> singleResults = results.getSingleResults();
		assertEquals(winner, singleResults.get(0));
	}

	@Test
	public void testBreakEven() {

		// prepare
		MultipleGameResults results = new MultipleGameResults();
		results.addResult(winner);
		results.addResult(loser);
		results.addResult(winner);
		results.addResult(loser);

		// verify win percentage
		assertEquals(50d, results.getWinPercentage());

		// verify results
		assertEquals(4, results.getAttempts());

		List<SingleGameResult> singleResults = results.getSingleResults();
		assertEquals(winner, singleResults.get(0));
		assertEquals(loser, singleResults.get(1));
		assertEquals(winner, singleResults.get(2));
		assertEquals(loser, singleResults.get(3));
	}

	@Test
	public void testBadNightOut() {

		// prepare
		MultipleGameResults results = new MultipleGameResults();
		results.addResult(loser);
		results.addResult(loser);
		results.addResult(loser);
		results.addResult(loser);
		results.addResult(loser);

		// verify win percentage
		assertEquals(0d, results.getWinPercentage());

		// verify results
		assertEquals(5, results.getAttempts());

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
