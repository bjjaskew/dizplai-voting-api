package com.dizplai.voting_api.controller;

import com.dizplai.voting_api.controller.requests.CreatePollOptionRequest;
import com.dizplai.voting_api.controller.requests.CreatePollRequest;
import com.dizplai.voting_api.controller.requests.CreateVoteRequest;
import com.dizplai.voting_api.exceptions.NotFoundException;
import com.dizplai.voting_api.exceptions.PollTooLargeException;
import com.dizplai.voting_api.service.OptionService;
import com.dizplai.voting_api.service.PollService;
import com.dizplai.voting_api.service.VoteService;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final VoteService voteService;

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

    @PostMapping(value = "/poll/{poll_id}/option")
    public ResponseEntity<?> createPollOptionByPollId(
            @PathVariable(value = "poll_id") Integer pollId,
            @Validated @RequestBody CreatePollOptionRequest request
    ) throws NotFoundException, PollTooLargeException {

        return ResponseEntity.ok(optionsService.createPollOption(pollId, request));
    }

    @GetMapping(value = "/poll/{poll_id}/votes")
    public ResponseEntity<?> getVotesByPollId(
            @PathVariable(value = "poll_id") Integer pollId
    ) {
        return ResponseEntity.ok(voteService.getVotesByPollId(pollId));
    }

    @PostMapping(value = "/poll/{poll_id}/vote")
    public ResponseEntity<?> createVote(
            @PathVariable(value = "poll_id") Integer pollId,
            @Validated @RequestBody CreateVoteRequest request
    ) throws NotFoundException {
        voteService.createVote(pollId, request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
