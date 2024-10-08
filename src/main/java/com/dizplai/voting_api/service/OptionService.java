package com.dizplai.voting_api.service;

import com.dizplai.voting_api.controller.requests.CreatePollOptionRequest;
import com.dizplai.voting_api.controller.responses.OptionResponse;
import com.dizplai.voting_api.data.entity.OptionEntity;
import com.dizplai.voting_api.data.repository.IOptionRepository;
import com.dizplai.voting_api.exceptions.NotFoundException;
import com.dizplai.voting_api.exceptions.PollTooLargeException;
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

    public OptionResponse createPollOption(Integer pollId, CreatePollOptionRequest request) throws NotFoundException, PollTooLargeException {
        pollService.getPollById(pollId);

        if (iOptionRepository.getByPollId(pollId).size() >=7) {
            throw new PollTooLargeException("A poll has a maximum of 7 options");
        }

        return  entityToResp(iOptionRepository.save(OptionEntity.builder()
                .pollId(pollId)
                .name(request.getName())
                .build()));
    }

    public OptionResponse getOptionById(Integer id) throws NotFoundException {
        return iOptionRepository.findById(id)
                .map(this::entityToResp)
                .orElseThrow(() -> new NotFoundException(String.format("Could not find option with id %s", id)));
    }

    private OptionResponse entityToResp(OptionEntity e) {
        return OptionResponse.builder()
                .id(e.getId())
                .name(e.getName())
                .build();
    }
}
