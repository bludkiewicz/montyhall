package com.bludkiewicz.montyhall.mvc.service;

import com.bludkiewicz.montyhall.core.service.enums.Door;
import com.bludkiewicz.montyhall.core.service.results.MultipleGameResults;
import com.bludkiewicz.montyhall.core.service.results.SingleGameResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OutputServiceTest {

	@InjectMocks
	private OutputService service;

	@Mock
	private FormattingService mock;

	@BeforeEach
	public void setup() {

		lenient().when(mock.getSingleGameResultsHeaders()).thenReturn(List.of());
		lenient().when(mock.getSingleGameResults(anyInt(), any())).thenReturn("");
		lenient().when(mock.getOverallResults(any())).thenReturn("");
	}

	@Test
	public void testServiceNoSingles() {

		// perform
		MultipleGameResults results = new MultipleGameResults(0, 10000, Collections.emptyList());
		service.processResults(results);

		// verify that singles are not output if attempts > max
		verify(mock, times(1)).getOverallResults(results);
		verify(mock, times(0)).getSingleGameResultsHeaders();
		verify(mock, times(0)).getSingleGameResults(anyInt(), any());
	}

	@Test
	public void testService() {

		// perform
		SingleGameResult result = new SingleGameResult(Door.ONE, Door.TWO, Door.THREE);
		MultipleGameResults results = new MultipleGameResults(0, 3, List.of(result, result, result));
		service.processResults(results);

		// verify that singles are not output if attempts > max
		verify(mock, times(1)).getOverallResults(results);
		verify(mock, times(1)).getSingleGameResultsHeaders();
		verify(mock, times(3)).getSingleGameResults(anyInt(), any());
	}
}
