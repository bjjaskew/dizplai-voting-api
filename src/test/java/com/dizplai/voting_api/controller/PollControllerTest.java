package com.dizplai.voting_api.controller;
import com.dizplai.voting_api.service.PollService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static java.util.Collections.EMPTY_LIST;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PollController.class)
@ContextConfiguration(classes = PollController.class)
public class PollControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PollService mockPollService;

    @Test
    void testGetActivePolls() throws Exception {
        when(mockPollService.getActivePolls()).thenReturn(EMPTY_LIST);

        String expectedJsonResponse = "[]";

        MvcResult result = this.mockMvc
                .perform(get("/polls?filter=active")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        verify(mockPollService).getActivePolls();
        JSONAssert.assertEquals(expectedJsonResponse, response, false);
    }

    @Test
    void testGetPolls() throws Exception {
        when(mockPollService.getPolls()).thenReturn(EMPTY_LIST);

        String expectedJsonResponse = "[]";

        MvcResult result = this.mockMvc
                .perform(get("/polls")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        verify(mockPollService).getPolls();
        JSONAssert.assertEquals(expectedJsonResponse, response, false);
    }
}