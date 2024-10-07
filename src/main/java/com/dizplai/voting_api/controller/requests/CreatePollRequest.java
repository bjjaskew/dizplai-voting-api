package com.dizplai.voting_api.controller.requests;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class CreatePollRequest {

    @NotNull
    @Size(min = 1, max = 64)
    private String name;

    @NotNull
    @Size(min = 1, max = 256)
    private String description;

    @NotNull
    @Future
    private LocalDateTime endDateTime;
}
