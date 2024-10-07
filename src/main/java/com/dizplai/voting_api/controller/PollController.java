package com.dizplai.voting_api.controller;

import com.dizplai.voting_api.service.PollService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class PollController {

    private final PollService pollService;

    @GetMapping(value = "/polls")
    public ResponseEntity<?> getPolls() {
        return ResponseEntity.ok(pollService.getActivePolls());
    }

}
