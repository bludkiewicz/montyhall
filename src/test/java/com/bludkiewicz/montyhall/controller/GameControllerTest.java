package com.bludkiewicz.montyhall.controller;

import com.bludkiewicz.montyhall.controller.exception.ControllerExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTest {

	@Autowired
	private MockMvc mockMvc;

	//
	// test http methods
	//

	@Test
	public void testGet() throws Exception {

		// get is allowed with path variables
		getWithPathVariables("1", "true")
				.andExpect(status().isOk());

		// get is not allowed with json content
		mockMvc.perform(get("/api/play/").contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(status().isMethodNotAllowed())
				.andExpect(content().string(startsWith(ControllerExceptionHandler.MESSAGE_PREFIX)));
	}

	@Test
	public void testPost() throws Exception {

		// post is not allowed with path variables
		mockMvc.perform(post("/api/play/1/true"))
				.andExpect(status().isMethodNotAllowed())
				.andExpect(content().string(startsWith(ControllerExceptionHandler.MESSAGE_PREFIX)));

		// post is allowed with json content
		postWithJSON("1", "true")
				.andExpect(status().isOk());
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
	// Helper Methods
	//

	private void testBadRequests(String iterations, String switchDoor) throws Exception {

		// test get request with path variables
		getWithPathVariables(iterations, switchDoor)
				.andExpect(status().isBadRequest())
				.andExpect(content().string(startsWith(ControllerExceptionHandler.MESSAGE_PREFIX)));

		// test post request with json content
		postWithJSON(iterations, switchDoor)
				.andExpect(status().isBadRequest())
				.andExpect(content().string(startsWith(ControllerExceptionHandler.MESSAGE_PREFIX)));
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
}
