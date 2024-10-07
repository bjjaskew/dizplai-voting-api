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
@Table(schema = "dizplai", name = "poll")
public class PollEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poll_id")
    private Integer id;

    @Column(name = "name")
    private String name;


    @Column(name = "description")
    private String description;


    @Column(name = "created_date")
    private LocalDateTime createdDate;


    @Column(name = "end_date")
    private LocalDateTime endDate;
}
