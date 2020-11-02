package com.bludkiewicz.montyhall.mvc.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testPage() throws Exception {

		// verifies our index page returns as expected
		// spring boot automatically maps "/" to index.html
		mockMvc.perform(get("/").contentType(MediaType.TEXT_HTML))
				.andExpect(status().isOk())
				.andExpect(view().name("forward:index.html"));
	}

	@Test
	public void testGet() throws Exception {

		// already have comprehensive unit tests on all components
		// just need a simple happy day test with everything wired together
		mockMvc.perform(get("/api/play/100/true"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.attempts").value(100));
	}

	@Test
	public void testPost() throws Exception {

		// already have comprehensive unit tests on all components
		// just need a simple happy day test with everything wired together
		mockMvc.perform(post("/api/play/")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"iterations\":100,\"switch\":false}"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.attempts").value(100));
	}
}
