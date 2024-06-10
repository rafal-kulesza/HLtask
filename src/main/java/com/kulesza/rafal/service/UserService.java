package com.kulesza.rafal.service;

import com.kulesza.rafal.exception.InvalidUserDataException;
import com.kulesza.rafal.exception.UserIsNotUniqueException;
import com.kulesza.rafal.model.User;
import com.kulesza.rafal.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String createUser(User user) {
        validateUserData(user);
        ensureUsernameIsUnique(user.getUsername());

        userRepository.save(user);

        return "User successfully created!";
    }

    private void validateUserData(User user) {
        validateAge(user.getAge());
        validateUsername(user.getUsername());
    }

    private void validateAge(Integer age) {
        if (age == null || age <= 0) {
            throw new InvalidUserDataException("Invalid age");
        }
    }

    private void validateUsername(String name) {
        if ((name == null || name.isBlank())) {
            throw new InvalidUserDataException("Invalid name");
        }
    }

    private void ensureUsernameIsUnique(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UserIsNotUniqueException();
        }
    }
}
