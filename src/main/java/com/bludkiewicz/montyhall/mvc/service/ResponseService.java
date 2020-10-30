package com.bludkiewicz.montyhall.mvc.service;

import com.bludkiewicz.montyhall.core.service.results.MultipleGameResults;
import com.bludkiewicz.montyhall.mvc.controller.json.Response;

/**
 * Service that responds to results.
 */
public interface ResponseService {

	Response processResults(MultipleGameResults results);
}
