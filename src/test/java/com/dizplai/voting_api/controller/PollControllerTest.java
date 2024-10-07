package com.dizplai.voting_api.controller;

import com.dizplai.voting_api.controller.requests.CreatePollOptionRequest;
import com.dizplai.voting_api.controller.requests.CreatePollRequest;
import com.dizplai.voting_api.controller.responses.OptionResponse;
import com.dizplai.voting_api.controller.responses.PollResponse;
import com.dizplai.voting_api.exceptions.NotFoundException;
import com.dizplai.voting_api.service.OptionService;
import com.dizplai.voting_api.service.PollService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.EMPTY_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PollController.class)
@ContextConfiguration(classes = PollController.class)
@Import(ExceptionsController.class)
public class PollControllerTest {

    private static final ObjectMapper OM = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PollService mockPollService;

    @MockBean
    private OptionService mockOptionService;

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

    @Test
    void testCreatePoll() throws Exception {

        CreatePollRequest request = CreatePollRequest.builder()
                .name("A poll")
                .description("A poll description")
                .endDateTime(LocalDateTime.MAX)
                .build();

        when(mockPollService.createPoll(any())).thenReturn(PollResponse.builder()
                .id(1)
                .name("A poll")
                .description("A poll description")
                .build());

        String expectedJsonResponse = """
                {
                  "id": 1,
                  "name": "A poll",
                  "description": "A poll description"
                }
                """;

        MvcResult result = this.mockMvc
                .perform(post("/poll")
                        .content(OM.writer().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        verify(mockPollService).createPoll(request);
        JSONAssert.assertEquals(expectedJsonResponse, response, false);
    }

    public static Stream<Arguments> testCreatePoll_validation_source() {
        return Stream.of(
                Arguments.of(
                        CreatePollRequest.builder()
                                .build(),
                        """ 
                                [
                                "description must not be null",
                                "name must not be null",
                                "endDateTime must not be null"
                                ]
                                """
                ),
                Arguments.of(
                        CreatePollRequest.builder()
                                .name(new String(new char[65]).replace('\0', 'a'))
                                .description(new String(new char[265]).replace('\0', 'a'))
                                .endDateTime(LocalDateTime.MIN)
                                .build(),
                        """
                                [
                                "name size must be between 1 and 64",
                                "endDateTime must be a future date",
                                "description size must be between 1 and 256"
                                ]
                                """
                )
        );
    }

    @ParameterizedTest
    @MethodSource("testCreatePoll_validation_source")
    void testCreatePoll_validation(CreatePollRequest request, String expectedResponse) throws Exception {

        MvcResult result = this.mockMvc
                .perform(post("/poll")
                        .content(OM.writer().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        verifyNoInteractions(mockPollService);
        JSONAssert.assertEquals(expectedResponse, response, false);
    }

    @Test
    void testGetPollOptionsByPollId() throws Exception {

        when(mockOptionService.getOptionsByPollId(any())).thenReturn(List.of(OptionResponse.builder()
                .id(1)
                .name("An Option")
                .build()));

        String expectedJsonResponse = """
                [{
                  "id": 1,
                  "name": "An Option"
                }]
                """;

        MvcResult result = this.mockMvc
                .perform(get("/poll/1/options")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        verify(mockOptionService).getOptionsByPollId(1);
        JSONAssert.assertEquals(expectedJsonResponse, response, false);
    }

    @Test
    void testGetPollOptionsByPollId_PollNotFound() throws Exception {

        when(mockOptionService.getOptionsByPollId(any())).thenThrow(new NotFoundException("Poll with ID 1 not found"));

        String expectedResponse = "Poll with ID 1 not found";

        MvcResult result = this.mockMvc
                .perform(get("/poll/1/options")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        verify(mockOptionService).getOptionsByPollId(1);
        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    void testCreatePollOption() throws Exception {

        CreatePollOptionRequest request = CreatePollOptionRequest.builder()
                .name("An Option")
                .build();

        when(mockOptionService.createPollOption(any(), any())).thenReturn(OptionResponse.builder()
                .id(1)
                .name("An Option")
                .build());

        String expectedJsonResponse = """
                {
                  "id": 1,
                  "name": "An Option"
                }
                """;

        MvcResult result = this.mockMvc
                .perform(post("/poll/1/option")
                        .content(OM.writer().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        verify(mockOptionService).createPollOption(1, request);
        JSONAssert.assertEquals(expectedJsonResponse, response, false);
    }

    @Test
    void testCreatePollOption_validation() throws Exception {

        CreatePollOptionRequest request = CreatePollOptionRequest.builder()
                .name(new String(new char[65]).replace('\0', 'a'))
                .build();

        String expectedJsonResponse = """
                ["name size must be between 1 and 64"]
                """;

        MvcResult result = this.mockMvc
                .perform(post("/poll/1/option")
                        .content(OM.writer().writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        verifyNoInteractions(mockOptionService);
        JSONAssert.assertEquals(expectedJsonResponse, response, false);
    }
}
