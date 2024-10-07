package com.dizplai.voting_api.service;

import com.dizplai.voting_api.controller.requests.CreateVoteRequest;
import com.dizplai.voting_api.controller.responses.VoteResponse;
import com.dizplai.voting_api.controller.responses.VotesAggregatedResponse;
import com.dizplai.voting_api.data.entity.VoteEntity;
import com.dizplai.voting_api.data.entity.VotePollOptionAggregationProjection;
import com.dizplai.voting_api.data.repository.IVoteRepository;
import com.dizplai.voting_api.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class VoteService {

    private OptionService optionService;
    private IVoteRepository iVoteRepository;
    private Clock clock;

    public void createVote(Integer pollId, CreateVoteRequest request) throws NotFoundException {
        optionService.getOptionById(request.getOption());
        iVoteRepository.save(VoteEntity.builder()
                .pollId(pollId)
                .optionsId(request.getOption())
                .date(LocalDateTime.now(clock))
                .build());
    }

    public List<VoteResponse> getVotesByPollId(Integer pollId) {
        return iVoteRepository.getVotesByPollId(pollId)
                .stream()
                .map(this::voteToResp)
                .toList();
    }

    public List<VotesAggregatedResponse> getAggregatedVotesByPollId(Integer pollId) {
        return iVoteRepository.getVotesByIdGroupedByOption(pollId)
                .stream()
                .map(this::entityToResp)
                .toList();
    }

    private VoteResponse voteToResp(VoteEntity ve) {
        return VoteResponse.builder()
                .id(ve.getId())
                .optionId(ve.getOptionsId())
                .date(ve.getDate())
                .build();
    }

    private VotesAggregatedResponse entityToResp(VotePollOptionAggregationProjection proj) {
        return VotesAggregatedResponse.builder()
                .optionId(proj.getOptionId())
                .voteCount(proj.getVoteCount())
                .build();
    }
}
