package com.dizplai.voting_api.controller.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class VotesAggregatedResponse {

    Integer optionId;
    Integer voteCount;
}
