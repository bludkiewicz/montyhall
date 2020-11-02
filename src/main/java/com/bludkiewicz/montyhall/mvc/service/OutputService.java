package com.bludkiewicz.montyhall.mvc.service;

import com.bludkiewicz.montyhall.core.service.results.MultipleGameResults;
import com.bludkiewicz.montyhall.core.service.results.SingleGameResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service that logs results.
 */
@Service
public class OutputService {

	private final static Logger log = LoggerFactory.getLogger(OutputService.class);

	private final FormattingService service;

	public OutputService(FormattingService service) {
		this.service = service;
	}

	public void processResults(MultipleGameResults results) {

		log.info(service.getOverallResults(results));
		if (!results.getSingleResults().isEmpty()) {

			service.getSingleGameResultsHeaders().forEach(log::info);

			List<SingleGameResult> games = results.getSingleResults();
			for (int i = 0, size = games.size(); i < size; i++) {
				log.info(service.getSingleGameResults(i, games.get(i)));
			}
		}
	}
}
