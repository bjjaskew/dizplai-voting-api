package com.dizplai.voting_api.service;

import com.dizplai.voting_api.controller.responses.PollResponse;
import com.dizplai.voting_api.data.repository.IPollRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PollService {

    private final IPollRepository iPollRepository;

    public List<?> getActivePolls() {
        return iPollRepository.getActivePolls(LocalDateTime.now())
                .stream()
                .map(e -> PollResponse.builder()
                        .id(e.getId())
                        .name(e.getName())
                        .description(e.getDescription())
                        .build())
                .toList();
    }
}
