package com.assistico.planner.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    private Instant expiryDate;
}
