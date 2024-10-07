package com.dizplai.voting_api.service;

import com.dizplai.voting_api.controller.requests.CreatePollOptionRequest;
import com.dizplai.voting_api.controller.requests.CreateVoteRequest;
import com.dizplai.voting_api.controller.responses.OptionResponse;
import com.dizplai.voting_api.controller.responses.PollResponse;
import com.dizplai.voting_api.controller.responses.VotesAggregatedResponse;
import com.dizplai.voting_api.data.entity.OptionEntity;
import com.dizplai.voting_api.data.entity.VoteEntity;
import com.dizplai.voting_api.data.entity.VotePollOptionAggregationProjection;
import com.dizplai.voting_api.data.repository.IOptionRepository;
import com.dizplai.voting_api.data.repository.IVoteRepository;
import com.dizplai.voting_api.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.test.context.ActiveProfiles;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class VoteServiceTest {

    @Mock
    private IVoteRepository mockIVoteRepository;

    @Mock
    private OptionService mockOptionService;

    @Mock
    private Clock mockClock;

    @InjectMocks
    private VoteService voteService;

    @Test
    void testCreateVote() throws NotFoundException {
        when(mockOptionService.getOptionById(1)).thenReturn(OptionResponse.builder()
                .build());

        when(mockClock.instant()).thenReturn(Instant.parse("-999999999-01-01T00:00:00Z"));
        when(mockClock.getZone()).thenReturn(ZoneId.of("UTC"));

        CreateVoteRequest request = CreateVoteRequest.builder()
                .option(1)
                .build();

        VoteEntity expectedSave = VoteEntity.builder()
                .pollId(1)
                .optionsId(1)
                .date(LocalDateTime.MIN)
                .build();
        voteService.createVote(1, request);

        verify(mockIVoteRepository).save(expectedSave);
    }

    @Test
    void testGetVotesByPollId() {
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        Map<String, Object> projectionPropertiesMap = Map.of(
                "voteCount", "10",
                "optionId", "1"
        );
        VotePollOptionAggregationProjection projection = factory.createProjection(VotePollOptionAggregationProjection.class, projectionPropertiesMap);
        when(mockIVoteRepository.getVotesByIdGroupedByOption(any())).thenReturn(List.of(projection));

        List<VotesAggregatedResponse> expected = List.of(VotesAggregatedResponse.builder()
                        .voteCount(10)
                        .optionId(1)
                .build());

        var response = voteService.getVotesByPollId(1);

        verify(mockIVoteRepository).getVotesByIdGroupedByOption(1);
        assertThat(response).isEqualTo(expected);
    }
}