package com.bludkiewicz.montyhall.mvc.service;

import com.bludkiewicz.montyhall.core.service.results.MultipleGameResults;
import com.bludkiewicz.montyhall.core.service.results.SingleGameResult;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service that formats results to be displayed as a fixed width String.
 */
@Service
public class FormattingService {

	public final static List<String> HEADERS =
			List.of("Iteration  Door 1 Door 2 Door 3  Original Selected   Win",
					"----------------------------------------------------------");

	public String getOverallResults(MultipleGameResults results) {
		return String.format("Win Percentage: %.2f%%", getWinPercentage(results.getWins(), results.getAttempts()));
	}

	private double getWinPercentage(int wins, int attempts) {
		return ((double) wins / attempts) * 100;
	}

	public List<String> getSingleGameResultsHeaders() {
		return HEADERS;
	}

	public String getSingleGameResults(int iteration, SingleGameResult result) {
		return String.format("%1$9s  %2$-6s %3$-6s %4$-6s  %5$8s %6$8s  %7$5s",
				iteration + 1, result.getDoorOne(), result.getDoorTwo(), result.getDoorThree(),
				result.getOriginalChoice(), result.getSelectedChoice(), result.isWinner());
	}
}
