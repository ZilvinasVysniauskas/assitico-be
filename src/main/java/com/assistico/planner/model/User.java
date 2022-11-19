package com.assistico.planner.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;


@Entity
@Data
@Builder
public class User implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String username;

    private String email;

    private String password;

    private Boolean enabled;

    private Instant createdAt;
}
