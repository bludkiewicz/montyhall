package com.bludkiewicz.montyhall.service;

import com.bludkiewicz.montyhall.controller.json.Response;
import com.bludkiewicz.montyhall.service.results.MultipleGameResults;

/**
 * Service that responds to results.
 */
public interface ResponseService {

	Response processResults(MultipleGameResults results);
}
