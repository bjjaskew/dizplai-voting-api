package com.dizplai.voting_api.data.repository;

import com.dizplai.voting_api.data.entity.PollEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IPollRepository extends JpaRepository<PollEntity, Integer> {

    @Query(value = """
            SELECT e FROM PollEntity e
            WHERE e.createdDate <= :currentDate
            AND e.endDate >= :currentDate
            """)
    List<PollEntity> getActivePolls(@Param("currentDate") LocalDateTime currentDate);
}
