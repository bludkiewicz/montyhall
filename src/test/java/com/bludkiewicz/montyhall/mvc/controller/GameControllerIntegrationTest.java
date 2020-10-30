package com.bludkiewicz.montyhall.mvc.controller;

import com.bludkiewicz.montyhall.core.service.GameService;
import com.bludkiewicz.montyhall.core.service.params.GameOptions;
import com.bludkiewicz.montyhall.core.service.results.MultipleGameResults;
import com.bludkiewicz.montyhall.mvc.controller.captor.ResultCaptor;
import com.bludkiewicz.montyhall.mvc.controller.json.Response;
import com.bludkiewicz.montyhall.mvc.service.ResponseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(GameController.class)
public class GameControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@SpyBean
	private GameService gameService;

	@SpyBean
	private ResponseService responseService;

	@Test
	public void testGet() throws Exception {

		// prepare
		// this will capture return values from spies
		// spies wrap production beans, they do not replace them
		ResultCaptor<MultipleGameResults> resultCaptor = new ResultCaptor<>();
		doAnswer(resultCaptor).when(gameService).simulate(anyInt(), any());

		ResultCaptor<Response> responseCaptor = new ResultCaptor<>();
		doAnswer(responseCaptor).when(responseService).processResults(any());

		// perform get request
		mockMvc.perform(get("/api/play/100/true"))
				.andExpect(status().isOk());

		// verify game service called correctly
		verify(gameService, times(1)).simulate(100, new GameOptions(true));

		// verify results captured
		MultipleGameResults capturedResults = resultCaptor.getResult();
		assertTrue(capturedResults.getWins() <= 100);
		assertEquals(100, capturedResults.getAttempts());
		assertEquals(100, capturedResults.getSingleResults().size());
		capturedResults.getSingleResults().forEach(result ->
				assertNotEquals(result.getOriginalChoice(), result.getSelectedChoice())
		);

		// verify response service called correctly
		verify(responseService, times(1)).processResults(capturedResults);

		// verify results captured
		Response capturedResponse = responseCaptor.getResult();
		assertNull(capturedResponse);
	}

	@Test
	public void testPost() throws Exception {

		// prepare
		// this will capture return values from spies
		// spies wrap production beans, they do not replace them
		ResultCaptor<MultipleGameResults> resultCaptor = new ResultCaptor<>();
		doAnswer(resultCaptor).when(gameService).simulate(anyInt(), any());

		ResultCaptor<Response> responseCaptor = new ResultCaptor<>();
		doAnswer(responseCaptor).when(responseService).processResults(any());

		// perform post request with json content
		mockMvc.perform(post("/api/play/")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"iterations\":100,\"switch\":false}"))
				.andExpect(status().isOk());

		// verify game service called correctly
		verify(gameService, times(1)).simulate(100, new GameOptions(false));

		// verify results captured
		MultipleGameResults capturedResults = resultCaptor.getResult();
		assertTrue(capturedResults.getWins() <= 100);
		assertEquals(100, capturedResults.getAttempts());
		assertEquals(100, capturedResults.getSingleResults().size());
		capturedResults.getSingleResults().forEach(result ->
				assertEquals(result.getOriginalChoice(), result.getSelectedChoice())
		);

		// verify response service called correctly
		verify(responseService, times(1)).processResults(capturedResults);

		// verify results captured
		Response capturedResponse = responseCaptor.getResult();
		assertNull(capturedResponse);
	}

	private ResultActions postWithJSON(String iterations, String switchDoor) throws Exception {

		// create json content
		String json = String.format("{\"iterations\":%s,\"switch\":%s}", iterations, switchDoor);

		// perform post request with json content
		return mockMvc.perform(post("/api/play/").contentType(MediaType.APPLICATION_JSON).content(json));
	}
}
