package com.bludkiewicz.montyhall.controller;

import com.bludkiewicz.montyhall.controller.json.GameParams;
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

	/**
	 * Runs simulations based on passing parameters as path variables using the GET method.
	 * Parameters are validated and violations are handled in ControllerExceptionHandler.
	 *
	 * I would love to the use GameParams class here to avoid validation duplication
	 * but I do not know of a way to map both @PathVariables and @JsonProperties to the same class.
	 */
	@GetMapping("/api/play/{iterations}/{switch}")
	public void play(@Valid @Positive @PathVariable Integer iterations,
					 @PathVariable("switch") Boolean switchDoor) {

		// something to let me know this works
		log.info("PathVariables [{}, {}]", iterations, switchDoor);
	}

	/**
	 * Runs simulations based on passing parameters as JSON using the POST method.
	 * Parameters are validated and violations are handled in ControllerExceptionHandler.
	 */
	@PostMapping("/api/play/")
	public void play(@Valid @RequestBody GameParams params) {

		// something to let me know this works
		log.info("RequestBody [{}]", params);
	}
}
