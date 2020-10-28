package com.bludkiewicz.montyhall.service;

import com.bludkiewicz.montyhall.controller.json.Response;
import com.bludkiewicz.montyhall.service.results.MultipleGameResults;
import com.bludkiewicz.montyhall.service.results.SingleGameResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service that logs results.
 */
@Service
public class LogResponseService implements ResponseService {

	private final static Logger log = LoggerFactory.getLogger(LogResponseService.class);

	@Value("${output.max_results_to_show:1000}")
	private int maxResults;

	private final FormattingService service;

	public LogResponseService(FormattingService service) {
		this.service = service;
	}

	public Response processResults(MultipleGameResults results) {

		log.info(service.getOverallResults(results));
		if (results.getAttempts() <= maxResults) {

			service.getSingleGameResultsHeaders().forEach(log::info);

			List<SingleGameResult> games = results.getSingleResults();
			for (int i = 0, size = games.size(); i < size; i++) {
				log.info(service.getSingleGameResults(i, games.get(i)));
			}
		}

		return null;
	}
}
