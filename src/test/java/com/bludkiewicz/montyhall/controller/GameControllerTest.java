package com.bludkiewicz.montyhall.controller;

import com.bludkiewicz.montyhall.service.GameService;
import com.bludkiewicz.montyhall.service.ResponseService;
import com.bludkiewicz.montyhall.service.params.GameOptions;
import com.bludkiewicz.montyhall.service.results.MultipleGameResults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(GameController.class)
public class GameControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GameService gameMock;

	@MockBean
	private ResponseService responseMock;

	@BeforeEach
	public void setupMock() {

		// this method may or may not be called
		// depending on whether validation may or may not have passed
		lenient().when(gameMock.simulate(anyInt(), any())).thenReturn(results);
		lenient().when(responseMock.processResults(results)).thenReturn(null);
	}

	//
	// test http method validation
	//

	@Test
	public void testGet() throws Exception {

		// get is not allowed with json content
		mockMvc.perform(get("/api/play/").contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(status().isMethodNotAllowed())
				.andExpect(jsonPath("$.message").exists());

		verifyMocks(0);

		// get is allowed with path variables
		getWithPathVariables("1", "true")
				.andExpect(status().isOk());

		verifyMocks(1);
	}

	@Test
	public void testPost() throws Exception {

		// post is not allowed with path variables
		mockMvc.perform(post("/api/play/1/true"))
				.andExpect(status().isMethodNotAllowed())
				.andExpect(jsonPath("$.message").exists());

		verifyMocks(0);

		// post is allowed with json content
		postWithJSON("1", "true")
				.andExpect(status().isOk());

		verifyMocks(1);
	}

	//
	// test parameter validations
	//

	@Test
	public void testNonNumericIterations() throws Exception {

		// iterations is not a number
		testBadRequests("NaN", "false");
	}

	@Test
	public void testNegativeIterations() throws Exception {

		// iterations is negative
		testBadRequests("-1", "true");
	}

	@Test
	public void testZeroIterations() throws Exception {

		// iterations is zero
		testBadRequests("0", "false");
	}

	@Test
	public void testTooManyIterations() throws Exception {

		// iterations > Integer.MAX_VALUE
		testBadRequests("2147483648", "true");
	}

	@Test
	public void testNonBooleanSwitch() throws Exception {

		// switch is not a boolean
		testBadRequests("1", "maybe");
	}

	//
	// verifies that service received correct parameters from controller
	//

	@Test
	public void testServiceParameters() throws Exception {

		// test get request with path variables
		getWithPathVariables("1000", "false")
				.andExpect(status().isOk());

		// verifies that service received correct parameters
		verify(gameMock, times(1)).simulate(1000, new GameOptions(false));
		verify(responseMock, times(1)).processResults(results);

		// test post request with json content
		postWithJSON("1", "true")
				.andExpect(status().isOk());

		// verifies that service received correct parameters
		verify(gameMock, times(1)).simulate(1, new GameOptions(true));
		verify(responseMock, times(2)).processResults(results);
	}

	//
	// Helper Methods
	//

	private void testBadRequests(String iterations, String switchDoor) throws Exception {

		// test get request with path variables
		getWithPathVariables(iterations, switchDoor)
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").exists());

		verifyMocks(0);

		// test post request with json content
		postWithJSON(iterations, switchDoor)
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").exists());

		verifyMocks(0);
	}

	private void verifyMocks(int simulate) {

		// verify validation worked
		verify(gameMock, times(simulate)).simulate(anyInt(), any());
		verify(responseMock, times(simulate)).processResults(any());
	}

	private ResultActions getWithPathVariables(String iterations, String switchDoor) throws Exception {

		// create url
		String url = String.format("/api/play/%s/%s", iterations, switchDoor);

		// perform get request with path variables
		return mockMvc.perform(get(url));
	}

	private ResultActions postWithJSON(String iterations, String switchDoor) throws Exception {

		// create json content
		String json = String.format("{\"iterations\":%s,\"switch\":%s}", iterations, switchDoor);

		// perform post request with json content
		return mockMvc.perform(post("/api/play/").contentType(MediaType.APPLICATION_JSON).content(json));
	}

	//
	// Helper Object
	//

	private final static MultipleGameResults results = new MultipleGameResults(0, 0, null);
}
