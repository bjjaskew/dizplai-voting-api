package com.dizplai.voting_api.service;

import com.dizplai.voting_api.controller.requests.CreatePollRequest;
import com.dizplai.voting_api.controller.responses.PollResponse;
import com.dizplai.voting_api.data.entity.PollEntity;
import com.dizplai.voting_api.data.repository.IPollRepository;
import com.dizplai.voting_api.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PollService {

    private final IPollRepository iPollRepository;
    private final Clock clock;

    public List<PollResponse> getActivePolls() {
        return iPollRepository.getActivePolls(LocalDateTime.now())
                .stream()
                .map(this::entityToResp)
                .toList();
    }

    public List<PollResponse> getPolls() {
        return iPollRepository.findAll()
                .stream()
                .map(this::entityToResp)
                .toList();
    }

    public PollResponse getPollById(Integer id) throws NotFoundException {
        return iPollRepository.findById(id)
                .map(this::entityToResp)
                .orElseThrow(() -> new NotFoundException(String.format("Could not find poll with id %s", id)));
    }

    private PollResponse entityToResp(PollEntity e) {
        return PollResponse.builder()
                .id(e.getId())
                .name(e.getName())
                .description(e.getDescription())
                .build();
    }

    public PollResponse createPoll(CreatePollRequest request) {
        return entityToResp(
                iPollRepository.save(PollEntity.builder()
                        .name(request.getName())
                        .description(request.getDescription())
                        .createdDate(LocalDateTime.now(clock))
                        .endDate(request.getEndDateTime())
                        .build()));
    }
}
