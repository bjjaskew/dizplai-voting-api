package com.dizplai.voting_api.controller.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CreatePollOptionRequest {

    @NotNull
    @Size(min = 1, max = 64)
    private String name;
}
