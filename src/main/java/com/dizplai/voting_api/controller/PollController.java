package com.dizplai.voting_api.controller;

import com.dizplai.voting_api.service.PollService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;
import java.util.Set;

@Controller
@AllArgsConstructor
public class PollController {

    private final PollService pollService;

    @GetMapping(value = "/polls")
    public ResponseEntity<?> getPolls(
            @RequestParam(value = "filter", required = false) Set<String> filters
    ) {
        if (Objects.nonNull(filters) && filters.contains("active")) {
            return ResponseEntity.ok(pollService.getActivePolls());
        }
        return ResponseEntity.ok(pollService.getPolls());
    }

}
