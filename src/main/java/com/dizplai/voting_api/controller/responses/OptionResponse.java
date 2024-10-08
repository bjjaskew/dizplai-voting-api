package com.dizplai.voting_api.controller.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class OptionResponse {
    private Integer id;
    private String name;
    private Integer votes;
}
