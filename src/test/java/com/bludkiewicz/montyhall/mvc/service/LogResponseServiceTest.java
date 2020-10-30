package com.bludkiewicz.montyhall.mvc.service;

import com.bludkiewicz.montyhall.core.service.enums.Door;
import com.bludkiewicz.montyhall.core.service.results.MultipleGameResults;
import com.bludkiewicz.montyhall.core.service.results.SingleGameResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Import(LogResponseService.class)
public class LogResponseServiceTest {

	@Autowired
	private LogResponseService service;

	@MockBean
	private FormattingService mock;

	@Value("${output.max_results_to_show:1000}")
	private int maxResults;

	@BeforeEach
	public void setup() {

		lenient().when(mock.getSingleGameResultsHeaders()).thenReturn(List.of());
		lenient().when(mock.getSingleGameResults(0, result)).thenReturn("");
		lenient().when(mock.getSingleGameResults(1, result)).thenReturn("");
		lenient().when(mock.getSingleGameResults(2, result)).thenReturn("");
	}

	@Test
	public void testServiceNoSingles() {

		// prepare
		MultipleGameResults results = new MultipleGameResults(0, (maxResults + 1), singleResults);
		when(mock.getOverallResults(results)).thenReturn("");

		service.processResults(results);

		// verify that singles are not output if attempts > max
		verify(mock, times(1)).getOverallResults(results);
		verify(mock, times(0)).getSingleGameResultsHeaders();
		verify(mock, times(0)).getSingleGameResults(0, result);
		verify(mock, times(0)).getSingleGameResults(1, result);
		verify(mock, times(0)).getSingleGameResults(2, result);
	}

	@Test
	public void testService() {

		// prepare
		MultipleGameResults results = new MultipleGameResults(0, 3, singleResults);
		when(mock.getOverallResults(results)).thenReturn("");

		service.processResults(results);

		// verify that singles are not output if attempts > max
		verify(mock, times(1)).getOverallResults(results);
		verify(mock, times(1)).getSingleGameResultsHeaders();
		verify(mock, times(1)).getSingleGameResults(0, result);
		verify(mock, times(1)).getSingleGameResults(1, result);
		verify(mock, times(1)).getSingleGameResults(2, result);
	}

	//
	// Helper Objects
	//

	private final static SingleGameResult result = new SingleGameResult(Door.ONE, Door.TWO, Door.THREE);
	private final static List<SingleGameResult> singleResults = List.of(result, result, result);
}
