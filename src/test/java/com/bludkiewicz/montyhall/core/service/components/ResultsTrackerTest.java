package com.bludkiewicz.montyhall.core.service.components;

import com.bludkiewicz.montyhall.core.service.enums.Door;
import com.bludkiewicz.montyhall.core.service.results.MultipleGameResults;
import com.bludkiewicz.montyhall.core.service.results.SingleGameResult;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResultsTrackerTest {

	@Test
	public void testBigWinnerAtMaxResults() {

		// prepare
		ResultsTracker generator = new ResultsTracker();
		ReflectionTestUtils.setField(generator, "maxResults", 1);

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
	public void testBreakEvenUnderMaxResults() {

		// prepare
		ResultsTracker generator = new ResultsTracker();
		ReflectionTestUtils.setField(generator, "maxResults", 5);

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
	public void testBadNightOutOverMaxResults() {

		// prepare
		ResultsTracker generator = new ResultsTracker();
		ReflectionTestUtils.setField(generator, "maxResults", 4);

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
		assertTrue(singleResults.isEmpty());
	}

	//
	// Helper Objects
	//

	private final static SingleGameResult winner = new SingleGameResult(Door.ONE, Door.TWO, Door.ONE);
	private final static SingleGameResult loser = new SingleGameResult(Door.ONE, Door.TWO, Door.THREE);
}
