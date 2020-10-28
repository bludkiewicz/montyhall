package com.bludkiewicz.montyhall.service.components;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotSame;

@SpringBootTest
public class ComponentFactoryTest {

	@Autowired
	private ComponentFactory factory;

	@Test
	public void testGameDoors() {
		// verify we're getting new instances
		assertNotSame(factory.getGameDoors(), factory.getGameDoors());
	}

	@Test
	public void testResultsTracker() {
		// verify we're getting new instances
		assertNotSame(factory.getResultsTracker(), factory.getResultsTracker());
	}
}
