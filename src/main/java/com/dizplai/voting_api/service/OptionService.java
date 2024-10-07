package com.dizplai.voting_api.service;

import com.dizplai.voting_api.controller.responses.OptionResponse;
import com.dizplai.voting_api.data.entity.OptionEntity;
import com.dizplai.voting_api.data.repository.IOptionRepository;
import com.dizplai.voting_api.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OptionService {

    private PollService pollService;
    private IOptionRepository iOptionRepository;

    public List<OptionResponse> getOptionsByPollId(Integer pollId) throws NotFoundException {
        pollService.getPollById(pollId);
        return iOptionRepository.getByPollId(pollId)
                .stream()
                .map(this::entityToResp)
                .toList();
    }

    private OptionResponse entityToResp(OptionEntity e) {
        return OptionResponse.builder()
                .id(e.getId())
                .name(e.getName())
                .build();
    }
}
