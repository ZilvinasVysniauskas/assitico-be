package com.assistico.planner.service.user;

import com.assistico.planner.model.User;
import com.assistico.planner.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Boolean isUserNameExisting(String username) {
        return findByUsername(username).isPresent();
    }

    public boolean isEmailExisting(String email) {
        return findByEmail(email).isPresent();
    }
}
