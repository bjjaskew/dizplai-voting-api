package com.dizplai.voting_api.service;

import com.dizplai.voting_api.controller.requests.CreatePollRequest;
import com.dizplai.voting_api.controller.responses.PollResponse;
import com.dizplai.voting_api.data.entity.PollEntity;
import com.dizplai.voting_api.data.repository.IPollRepository;
import com.dizplai.voting_api.exceptions.NotFoundException;
import org.hibernate.annotations.Parameter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.repository.query.Param;
import org.springframework.test.context.ActiveProfiles;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Collections.EMPTY_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class PollServiceTest {

    @Mock
    private IPollRepository mockIPollRepository;

    @Mock
    private Clock mockClock;

    @InjectMocks
    private PollService pollService;

    public static Stream<Arguments> testPolls_source() {
        return Stream.of(
                Arguments.of(
                        List.of(PollEntity.builder()
                                .id(1)
                                .name("A poll")
                                .description("A poll description")
                                .createdDate(LocalDateTime.MIN)
                                .endDate(LocalDateTime.MAX)
                                .build()),
                        List.of(PollResponse.builder()
                                .id(1)
                                .name("A poll")
                                .description("A poll description")
                                .build())
                ),
                Arguments.of(EMPTY_LIST, EMPTY_LIST)
        );
    }

    @ParameterizedTest
    @MethodSource("testPolls_source")
    void testGetActivePolls(List<PollEntity> repositoryResponse, List<PollResponse> expectedResponse) {
        when(mockIPollRepository.getActivePolls(any())).thenReturn(repositoryResponse);

        assertThat(pollService.getActivePolls()).isEqualTo(expectedResponse);
        verify(mockIPollRepository).getActivePolls(any());

    }

    @ParameterizedTest
    @MethodSource("testPolls_source")
    void testGetPolls(List<PollEntity> repositoryResponse, List<PollResponse> expectedResponse) {
        when(mockIPollRepository.findAll()).thenReturn(repositoryResponse);

        assertThat(pollService.getPolls()).isEqualTo(expectedResponse);
        verify(mockIPollRepository).findAll();

    }

    @Test
    void testCreatePoll() {
        PollEntity expectedRepositoryResponse = PollEntity.builder()
                .id(1)
                .name("A poll")
                .description("A poll description")
                .createdDate(LocalDateTime.MIN)
                .endDate(LocalDateTime.MAX)
                .build();

        PollResponse expectedResponse = PollResponse.builder()
                .id(1)
                .name("A poll")
                .description("A poll description")
                .build();

        CreatePollRequest createPollRequest = CreatePollRequest.builder()
                .name("A poll")
                .description("A poll description")
                .endDateTime(LocalDateTime.MAX)
                .build();

        when(mockIPollRepository.save(any())).thenReturn(expectedRepositoryResponse);
        when(mockClock.instant()).thenReturn(Instant.parse("-999999999-01-01T00:00:00Z"));
        when(mockClock.getZone()).thenReturn(ZoneId.of("UTC"));

        var response = pollService.createPoll(createPollRequest);
        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    void testGetPollById() throws NotFoundException {
        PollEntity expectedRepositoryResponse = PollEntity.builder()
                .id(1)
                .name("A poll")
                .description("A poll description")
                .createdDate(LocalDateTime.MIN)
                .endDate(LocalDateTime.MAX)
                .build();

        PollResponse expectedResponse = PollResponse.builder()
                .id(1)
                .name("A poll")
                .description("A poll description")
                .build();

        when(mockIPollRepository.findById(any())).thenReturn(Optional.of(expectedRepositoryResponse));

        PollResponse response = pollService.getPollById(1);
        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    void testGetPollById_FailsExpectedly() {
        when(mockIPollRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> pollService.getPollById(1));
    }
}
