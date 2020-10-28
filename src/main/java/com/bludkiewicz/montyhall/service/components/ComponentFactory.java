package com.bludkiewicz.montyhall.service.components;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Convenience class to generate prototype components as needed.
 */
@Component
public class ComponentFactory {

	private final ApplicationContext context;

	public ComponentFactory( ApplicationContext context ) {
		this.context = context;
	}

	public GameDoors getGameDoors() {
		return context.getBean(GameDoors.class);
	}

	public ResultsTracker getResultsTracker() {
		return context.getBean(ResultsTracker.class);
	}
}
