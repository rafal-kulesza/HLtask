package com.kulesza.rafal.controller;

import com.kulesza.rafal.model.User;
import com.kulesza.rafal.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        return ResponseEntity.ok().body(userService.createUser(user));
    }
}
