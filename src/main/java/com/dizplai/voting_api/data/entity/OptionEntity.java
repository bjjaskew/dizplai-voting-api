package com.dizplai.voting_api.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "dizplai", name = "options")
public class OptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "options_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "poll_id")
    private Integer pollId;
}
