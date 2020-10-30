package com.bludkiewicz.montyhall.mvc.controller;

import com.bludkiewicz.montyhall.core.service.GameService;
import com.bludkiewicz.montyhall.core.service.params.GameOptions;
import com.bludkiewicz.montyhall.core.service.results.MultipleGameResults;
import com.bludkiewicz.montyhall.mvc.controller.json.GameParams;
import com.bludkiewicz.montyhall.mvc.controller.json.Response;
import com.bludkiewicz.montyhall.mvc.service.ResponseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@Validated
public class GameController {

	private static final Logger log = LoggerFactory.getLogger(GameController.class);

	private final GameService gameService;
	private final ResponseService responseService;

	public GameController(GameService gameService, ResponseService responseService) {
		this.gameService = gameService;
		this.responseService = responseService;
	}

	/**
	 * Runs simulations based on passing parameters as path variables using the GET method.
	 * Parameters are validated and violations are handled in ControllerExceptionHandler.
	 * <p>
	 * I would love to the use GameParams class here to avoid validation duplication
	 * but I do not know of a way to map both @PathVariables and @JsonProperties to the same class.
	 */
	@GetMapping("/api/play/{iterations}/{switch}")
	public Response play(@Valid @Positive @PathVariable Integer iterations,
						 @PathVariable("switch") Boolean switchDoors) {

		log.debug("PathVariables Received: {}, {}", iterations, switchDoors);

		return playTheGame(iterations, switchDoors);
	}

	/**
	 * Runs simulations based on passing parameters as JSON using the POST method.
	 * Parameters are validated and violations are handled in ControllerExceptionHandler.
	 */
	@PostMapping("/api/play/")
	public Response play(@Valid @RequestBody GameParams params) {

		log.debug("RequestBody Received: {}", params);

		return playTheGame(params.getIterations(), params.getSwitchDoor());
	}

	/**
	 * No matter how the request is received, the game is played the same.
	 */
	private Response playTheGame(int iterations, boolean switchDoors) {
		MultipleGameResults results =
				gameService.simulate(iterations, new GameOptions(switchDoors));

		return responseService.processResults(results);
	}
}
