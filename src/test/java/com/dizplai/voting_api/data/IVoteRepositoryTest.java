package com.dizplai.voting_api.data;

import com.dizplai.voting_api.data.entity.VotePollOptionAggregationProjection;
import com.dizplai.voting_api.data.repository.IVoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
        "spring.flyway.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class IVoteRepositoryTest {

    @Autowired
    private IVoteRepository iVoteRepository;

    @Test
    @Sql(scripts = "classpath:sql/testVotes/votingDetails.sql")
    public void testGetVoteAggregate() {
        List<VotePollOptionAggregationProjection> response = iVoteRepository.getVotesByIdGroupedByOption(1);
        assertThat(response.size()).isEqualTo(2);
    }
}

