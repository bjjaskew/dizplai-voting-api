package com.dizplai.voting_api.controller.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class VoteResponse {
    private Integer id;
    private Integer optionId;
    private LocalDateTime date;
}
