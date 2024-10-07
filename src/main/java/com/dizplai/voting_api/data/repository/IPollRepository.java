package com.dizplai.voting_api.data.repository;

import com.dizplai.voting_api.data.entity.PollEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPollRepository extends JpaRepository<PollEntity, Integer> {
}
