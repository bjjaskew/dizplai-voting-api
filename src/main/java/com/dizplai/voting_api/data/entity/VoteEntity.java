package com.dizplai.voting_api.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "dizplai", name = "vote")
public class VoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id")
    private Integer id;

    @Column(name = "poll_id")
    private Integer pollId;

    @Column(name = "options_id")
    private Integer options_id;

    @Column(name = "vote_date")
    private LocalDateTime date;
}
