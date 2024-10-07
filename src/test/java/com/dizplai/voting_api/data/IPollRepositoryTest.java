package com.dizplai.voting_api.data;

import com.dizplai.voting_api.data.entity.PollEntity;
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
public class IPollRepositoryTest {

    @Autowired
    private IPollRepository iPollRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testGetActivePolls_NoData() {
        List<PollEntity> response = iPollRepository.getActivePolls(LocalDateTime.now());
        assertThat(response.size()).isEqualTo(0);
    }


    @Test
    @Sql(scripts = "classpath:sql/testGetActivePolls/noActivePolls.sql")
    public void testGetActivePolls_noActivePolls() {
        List<PollEntity> response = iPollRepository.getActivePolls(LocalDateTime.now());
        assertThat(response.size()).isEqualTo(0);
    }


    @Test
    @Sql(scripts = "classpath:sql/testGetActivePolls/activePolls.sql")
    public void testGetActivePolls_activePolls() {
        List<PollEntity> response = iPollRepository.getActivePolls(LocalDateTime.now());
        assertThat(response.size()).isEqualTo(1);
    }
}

