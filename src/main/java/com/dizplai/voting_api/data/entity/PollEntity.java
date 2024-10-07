package com.dizplai.voting_api.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
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
