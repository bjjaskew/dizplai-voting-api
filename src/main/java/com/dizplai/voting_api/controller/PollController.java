package com.dizplai.voting_api.controller;

import com.dizplai.voting_api.controller.requests.CreatePollRequest;
import com.dizplai.voting_api.exceptions.NotFoundException;
import com.dizplai.voting_api.service.OptionService;
import com.dizplai.voting_api.service.PollService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Set;

@Controller
@AllArgsConstructor
public class PollController {

    private final PollService pollService;
    private final OptionService optionsService;

    @GetMapping(value = "/polls")
    public ResponseEntity<?> getPolls(
            @RequestParam(value = "filter", required = false) Set<String> filters) {
        if (Objects.nonNull(filters) && filters.contains("active")) {
            return ResponseEntity.ok(pollService.getActivePolls());
        }
        return ResponseEntity.ok(pollService.getPolls());
    }

    @PostMapping(value = "/poll")
    public ResponseEntity<?> createPoll(
            @Validated @RequestBody CreatePollRequest request) {
        return ResponseEntity.ok(pollService.createPoll(request));
    }

    @GetMapping(value = "/poll/{poll_id}/options")
    public ResponseEntity<?> getPollOptionsByPollId(
            @PathVariable(value = "poll_id") Integer pollId) throws NotFoundException {

        return ResponseEntity.ok(optionsService.getOptionsByPollId(pollId));
    }
}
