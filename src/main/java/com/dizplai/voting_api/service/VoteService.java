package com.dizplai.voting_api.service;

import com.dizplai.voting_api.controller.requests.CreateVoteRequest;
import com.dizplai.voting_api.data.entity.VoteEntity;
import com.dizplai.voting_api.data.repository.IVoteRepository;
import com.dizplai.voting_api.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;

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
                .options_id(request.getOption())
                .date(LocalDateTime.now(clock))
                .build());
    }
}
