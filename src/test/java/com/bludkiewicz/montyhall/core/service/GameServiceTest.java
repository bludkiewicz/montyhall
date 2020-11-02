package com.bludkiewicz.montyhall.core.service;

import com.bludkiewicz.montyhall.core.service.components.ComponentFactory;
import com.bludkiewicz.montyhall.core.service.components.GameDoors;
import com.bludkiewicz.montyhall.core.service.components.ResultsTracker;
import com.bludkiewicz.montyhall.core.service.enums.Door;
import com.bludkiewicz.montyhall.core.service.params.GameOptions;
import com.bludkiewicz.montyhall.core.service.results.MultipleGameResults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

	@InjectMocks
	private GameService service;

	@Mock
	private ComponentFactory factory;

	@BeforeEach
	public void setup() {
		lenient().when(factory.getGameDoors()).thenReturn(new GameDoors());

		ResultsTracker tracker = new ResultsTracker();
		ReflectionTestUtils.setField(tracker, "maxResults", 1000);
		lenient().when(factory.getResultsTracker()).thenReturn(tracker);
	}

	@Test
	public void testSwitchedDoorsAndRandomness() {

		int iterations = 100;
		MultipleGameResults results = service.simulate(iterations, new GameOptions(true));

		// verify correct number of iterations
		assertEquals(iterations, results.getAttempts());

		// once again, it's really hard to test for randomness
		Set<Door> originalSelections = new HashSet<>(new GameDoors().getPossibleDoorOptions());
		results.getSingleResults().forEach(result -> {
			// verify door was switched
			assertNotSame(result.getOriginalChoice(), result.getSelectedChoice());
			if (!originalSelections.isEmpty()) originalSelections.remove(result.getOriginalChoice());
		});

		// randomness check
		assertEquals(0, originalSelections.size());

		// verify components were created correct number of times
		verifyMocks(1, iterations);
	}

	@Test
	public void testSameDoors() {

		int iterations = 100;
		MultipleGameResults results = service.simulate(iterations, new GameOptions(false));

		// verify correct number of iterations
		assertEquals(iterations, results.getAttempts());

		results.getSingleResults().forEach(result ->
				// verify door was not switched
				assertSame(result.getOriginalChoice(), result.getSelectedChoice())
		);

		// verify components were created correct number of times
		verifyMocks(1, iterations);
	}

	private void verifyMocks(int tracker, int doors) {
		verify(factory, times(tracker)).getResultsTracker();
		verify(factory, times(doors)).getGameDoors();
	}
}
