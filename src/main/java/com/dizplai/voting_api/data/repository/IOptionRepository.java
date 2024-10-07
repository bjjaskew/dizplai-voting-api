package com.dizplai.voting_api.data.repository;

import com.dizplai.voting_api.data.entity.OptionEntity;
import com.dizplai.voting_api.data.entity.PollEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IOptionRepository extends JpaRepository<OptionEntity, Integer> {

    @Query(value = """
            SELECT e FROM OptionEntity e
            WHERE e.pollId = :pollId
            """)
    List<OptionEntity> getByPollId(@Param("pollId") Integer pollId);
}
