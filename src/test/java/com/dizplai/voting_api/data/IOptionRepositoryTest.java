package com.dizplai.voting_api.data;

import com.dizplai.voting_api.data.entity.OptionEntity;
import com.dizplai.voting_api.data.entity.PollEntity;
import com.dizplai.voting_api.data.repository.IOptionRepository;
import com.dizplai.voting_api.data.repository.IPollRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
        "spring.flyway.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class IOptionRepositoryTest {

    @Autowired
    private IOptionRepository iOptionRepository;

    @Test
    public void testGetOptionsByPollId() {
        List<OptionEntity> response = iOptionRepository.getByPollId(1);
        assertThat(response.size()).isEqualTo(0);
    }


    @Test
    @Sql(scripts = "classpath:sql/testGetOptions/options.sql")
    public void testGetActivePolls_noActivePolls() {
        List<OptionEntity> response = iOptionRepository.getByPollId(1);
        assertThat(response.size()).isEqualTo(2);
    }
}

