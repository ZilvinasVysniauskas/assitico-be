package com.assistico.planner.repository.user;

import com.assistico.planner.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Override
    Page<User> findAll(Pageable pageable);

    Optional<User> findByEmail(String email);
}
