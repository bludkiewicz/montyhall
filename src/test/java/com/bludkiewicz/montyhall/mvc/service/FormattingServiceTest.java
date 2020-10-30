package com.bludkiewicz.montyhall.mvc.service;

import com.bludkiewicz.montyhall.core.service.components.ResultsTracker;
import com.bludkiewicz.montyhall.core.service.enums.Door;
import com.bludkiewicz.montyhall.core.service.enums.Prize;
import com.bludkiewicz.montyhall.core.service.results.MultipleGameResults;
import com.bludkiewicz.montyhall.core.service.results.SingleGameResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormattingServiceTest {

	@Test
	public void testFormatting() {

		// prepare
		SingleGameResult resultOne = new SingleGameResult(Door.ONE, Door.ONE, Door.TWO);
		SingleGameResult resultTwo = new SingleGameResult(Door.TWO, Door.ONE, Door.THREE);
		SingleGameResult resultThree = new SingleGameResult(Door.THREE, Door.TWO, Door.THREE);

		ResultsTracker generator = new ResultsTracker();
		generator.addResult(resultOne);
		generator.addResult(resultTwo);
		generator.addResult(resultThree);

		MultipleGameResults results = generator.getMultipleGameResults();
		FormattingService service = new FormattingService();

		// verify win percentage is correct
		assertEquals("Win Percentage: 33.33%", service.getOverallResults(results));

		// verify header is correct
		assertEquals(FormattingService.HEADERS, service.getSingleGameResultsHeaders());

		// verify single game results are correct
		assertEquals("        1  " + Prize.CAR + "     " + Prize.GOAT + "     " + Prize.GOAT + "             1        2  false", service.getSingleGameResults(0, resultOne));
		assertEquals("        2  " + Prize.GOAT + "     " + Prize.CAR + "     " + Prize.GOAT + "             1        3  false", service.getSingleGameResults(1, resultTwo));
		assertEquals("        3  " + Prize.GOAT + "     " + Prize.GOAT + "     " + Prize.CAR + "             2        3   true", service.getSingleGameResults(2, resultThree));
	}
}
