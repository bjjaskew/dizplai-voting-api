package com.dizplai.voting_api.service;

import com.dizplai.voting_api.controller.requests.CreatePollOptionRequest;
import com.dizplai.voting_api.controller.responses.OptionResponse;
import com.dizplai.voting_api.controller.responses.PollResponse;
import com.dizplai.voting_api.data.entity.OptionEntity;
import com.dizplai.voting_api.data.repository.IOptionRepository;
import com.dizplai.voting_api.exceptions.NotFoundException;
import com.dizplai.voting_api.exceptions.PollTooLargeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.EMPTY_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class OptionServiceTest {

    @Mock
    private IOptionRepository mockIOptionRepository;

    @Mock
    private PollService mockPollService;

    @InjectMocks
    private OptionService optionService;

    @Test
    void testGetOptionsByPollId() throws NotFoundException {
        PollResponse pollResponse = PollResponse.builder()
                .id(1)
                .name("A poll")
                .description("A poll description")
                .build();

        OptionEntity expectedRepositoryResponse = OptionEntity.builder()
                .id(1)
                .name("An option")
                .build();

        List<OptionResponse> expectedResponse = List.of(OptionResponse.builder()
                .id(1)
                .name("An option")
                .build());

        when(mockPollService.getPollById(any())).thenReturn(pollResponse);
        when(mockIOptionRepository.getByPollId(any())).thenReturn(List.of(expectedRepositoryResponse));

        List<OptionResponse> response = optionService.getOptionsByPollId(1);
        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    void testCreatePollOption() throws NotFoundException, PollTooLargeException {
        PollResponse pollResponse = PollResponse.builder()
                .id(1)
                .name("A poll")
                .description("A poll description")
                .build();

        OptionEntity expectedRepositoryResponse = OptionEntity.builder()
                .id(1)
                .name("An option")
                .build();

        OptionResponse expectedResponse = OptionResponse.builder()
                .id(1)
                .name("An option")
                .build();

        CreatePollOptionRequest request = CreatePollOptionRequest.builder()
                .name("An option")
                .build();

        when(mockPollService.getPollById(any())).thenReturn(pollResponse);
        when(mockIOptionRepository.save(any())).thenReturn(expectedRepositoryResponse);
        when(mockIOptionRepository.getByPollId(1)).thenReturn(EMPTY_LIST);

        OptionResponse response = optionService.createPollOption(1, request);
        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    void testCreatePollOption_FailsWhenPollTooLarge() throws NotFoundException, PollTooLargeException {
        PollResponse pollResponse = PollResponse.builder()
                .id(1)
                .name("A poll")
                .description("A poll description")
                .build();

        List<OptionEntity> expectedRepositoryResponse = List.of(
                OptionEntity.builder().id(1).name("An option").build(),
                OptionEntity.builder().id(2).name("An option").build(),
                OptionEntity.builder().id(3).name("An option").build(),
                OptionEntity.builder().id(4).name("An option").build(),
                OptionEntity.builder().id(5).name("An option").build(),
                OptionEntity.builder().id(6).name("An option").build(),
                OptionEntity.builder().id(7).name("An option").build()
        );

        CreatePollOptionRequest request = CreatePollOptionRequest.builder()
                .name("An option")
                .build();

        when(mockPollService.getPollById(any())).thenReturn(pollResponse);
        when(mockIOptionRepository.getByPollId(any())).thenReturn(expectedRepositoryResponse);

        assertThrows(PollTooLargeException.class, () -> optionService.createPollOption(1, request));
    }

    @Test
    void testGetOptionById() throws NotFoundException {
        OptionEntity expectedRepositoryResponse = OptionEntity.builder()
                .id(1)
                .name("An option")
                .build();

        OptionResponse expectedResponse = OptionResponse.builder()
                .id(1)
                .name("An option")
                .build();

        when(mockIOptionRepository.findById(any())).thenReturn(Optional.of(expectedRepositoryResponse));

        OptionResponse response = optionService.getOptionById(1);
        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    void testGetPollById_FailsExpectedly() {
        when(mockIOptionRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> optionService.getOptionById(1));
    }

}
