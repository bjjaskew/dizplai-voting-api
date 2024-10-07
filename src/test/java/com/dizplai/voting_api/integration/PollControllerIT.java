package com.dizplai.voting_api.integration;

import com.dizplai.voting_api.controller.requests.CreatePollRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PollControllerIT {

    private static final ObjectMapper OM = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @LocalServerPort
    private int port;

    private static final TestRestTemplate testRestTemplate = new TestRestTemplate();

    @Test
    @Sql(scripts = {
            "classpath:/sql/testGetPolls/activePolls.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void test_getActivePolls() throws JSONException {
        String expectedResponse = """
                [{
                    "id" : 1,
                    "name" : "Bens poll",
                    "description" : "a description"
                }]
                """;

                var response = testRestTemplate.getForEntity(baseURLBuilder("/polls?filter=active"), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        JSONAssert.assertEquals(expectedResponse, response.getBody(), JSONCompareMode.LENIENT);
    }

    @Test
    @Sql(scripts = {
            "classpath:/sql/testGetPolls/activePolls.sql",
            "classpath:/sql/testGetPolls/noActivePolls.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void test_getPolls() throws JSONException {
        String expectedResponse = """
                [{
                    "id" : 1,
                    "name" : "Bens poll",
                    "description" : "a description"
                },
                {
                    "id" : 2,
                    "name" : "Bens inactive poll",
                    "description" : "another description"
                }]
                """;

        var response = testRestTemplate.getForEntity(baseURLBuilder("/polls"), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        JSONAssert.assertEquals(expectedResponse, response.getBody(), JSONCompareMode.LENIENT);
    }

    @Test
    void test_createPoll() throws JSONException, JsonProcessingException {
        String expectedResponse = """
                {
                    "id" : 1,
                    "name" : "Bens poll",
                    "description" : "a description"
                }
                """;

        CreatePollRequest requestBody = CreatePollRequest.builder()
                .name("A poll")
                .description("A poll description")
                .endDateTime(LocalDateTime.MAX)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> request = new HttpEntity<>(OM.writeValueAsString(requestBody),headers);

        var response = testRestTemplate.postForEntity(baseURLBuilder("/polls"), request,  String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        JSONAssert.assertEquals(expectedResponse, response.getBody(), JSONCompareMode.LENIENT);
    }

    private String baseURLBuilder(final String uri) {
        return String.format("http://localhost:%s%s", port, uri);
    }
}
