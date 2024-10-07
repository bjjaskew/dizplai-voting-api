package com.dizplai.voting_api.data.repository;

import com.dizplai.voting_api.data.entity.VoteEntity;
import com.dizplai.voting_api.data.entity.VotePollOptionAggregationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVoteRepository extends JpaRepository<VoteEntity, Integer> {

    @Query(value = """
            SELECT 
            ve.optionsId as optionId,
            count(ve.id) as voteCount
            FROM VoteEntity ve
            WHERE ve.pollId = :pollId
            GROUP BY ve.optionsId
            """)
    List<VotePollOptionAggregationProjection> getVotesByIdGroupedByOption(@Param("pollId") Integer pollId);
}
