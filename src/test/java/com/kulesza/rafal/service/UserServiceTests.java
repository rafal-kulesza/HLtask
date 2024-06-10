package com.kulesza.rafal.service;

import com.kulesza.rafal.exception.InvalidUserDataException;
import com.kulesza.rafal.exception.UserDoesNotExistException;
import com.kulesza.rafal.exception.UserIsNotUniqueException;
import com.kulesza.rafal.model.Gender;
import com.kulesza.rafal.model.User;
import com.kulesza.rafal.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        try (AutoCloseable openedMocks = MockitoAnnotations.openMocks(this)) {
            userService = new UserService(userRepository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    public void cleanup() {
        Mockito.reset(userRepository);
    }

    @Test
    void creating_user_should_return_response_message_about_success() {
        // GIVEN
        User user = new User();
        user.setUsername("Marian");
        user.setAge(54);
        user.setGender(Gender.MAN);
        // WHEN
        String responseMessage = userService.createUser(user);
        // THEN
        assertThat(responseMessage).isEqualTo("User successfully created!");
    }

    @Test
    void creating_user_with_null_age_should_throw_exception() {
        // GIVEN
        User user = new User();
        user.setUsername("Katarzyna");
        user.setAge(null);
        user.setGender(Gender.WOMAN);
        // WHEN
        Throwable exception = assertThrows(InvalidUserDataException.class, () -> userService.createUser(user));
        // THEN
        assertThat(exception.getMessage()).isEqualTo("Invalid age");
    }

    @Test
    void creating_user_with_wrong_age_should_throw_exception() {
        // GIVEN
        User user = new User();
        user.setUsername("Krzysztof");
        user.setAge(-4);
        user.setGender(Gender.OTHER);
        // WHEN
        Throwable exception = assertThrows(InvalidUserDataException.class, () -> userService.createUser(user));
        // THEN
        assertThat(exception.getMessage()).isEqualTo("Invalid age");
    }

    @Test
    void creating_user_with_null_name_should_throw_exception() {
        // GIVEN
        User user = new User();
        user.setUsername(null);
        user.setAge(18);
        user.setGender(Gender.OTHER);
        // WHEN
        Throwable exception = assertThrows(InvalidUserDataException.class, () -> userService.createUser(user));
        // THEN
        assertThat(exception.getMessage()).isEqualTo("Invalid name");
    }

    @Test
    void creating_user_with_blank_name_should_throw_exception() {
        // GIVEN
        User user = new User();
        user.setUsername(" ");
        user.setAge(18);
        user.setGender(Gender.OTHER);
        // WHEN
        Throwable exception = assertThrows(InvalidUserDataException.class, () -> userService.createUser(user));
        // THEN
        assertThat(exception.getMessage()).isEqualTo("Invalid name");
    }

    @Test
    void creating_duplicated_user_should_throw_exception() {
        // GIVEN
        User user = new User();

        user.setUsername("Marian");
        user.setAge(54);
        user.setGender(Gender.OTHER);
        // WHEN
        when(userRepository.existsByUsername("Marian")).thenReturn(true);
        Throwable exception = assertThrows(UserIsNotUniqueException.class, () -> userService.createUser(user));
        // THEN
        assertThat(exception).isInstanceOf(UserIsNotUniqueException.class);
    }

    @Test
    void getting_user_by_id_should_return_not_null_user() {
        // GIVEN
        String uuid = "4e618c9d-dc98-4a15-97a7-5d66570f1ff1";
        // WHEN
        when(userRepository.findById(UUID.fromString(uuid))).thenReturn(Optional.of(new User()));
        User user = userService.getUser(uuid);
        // THEN
        assertThat(user).isNotNull();
    }

    @Test
    void getting_not_existing_user_by_id_should_return_null() {
        // GIVEN
        String uuid = "4e618c9d-dc98-4a15-97a7-5d66570f1ff1";
        // WHEN
        when(userRepository.findById(UUID.fromString(uuid))).thenReturn(Optional.empty());
        Throwable exception = assertThrows(UserDoesNotExistException.class, () -> userService.getUser(uuid));
        // THEN
        assertThat(exception).isInstanceOf(UserDoesNotExistException.class);
    }
