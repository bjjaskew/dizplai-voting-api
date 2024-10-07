package com.dizplai.voting_api.service;

import com.dizplai.voting_api.controller.responses.PollResponse;
import com.dizplai.voting_api.data.entity.PollEntity;
import com.dizplai.voting_api.data.repository.IPollRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PollService {

    private final IPollRepository iPollRepository;

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

    private PollResponse entityToResp(PollEntity e) {
        return PollResponse.builder()
                .id(e.getId())
                .name(e.getName())
                .description(e.getDescription())
                .build();
    }
}
