package com.example.user.controller;

import com.example.user.domain.User;
import com.example.user.dto.UserDto;
import com.example.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveAndPublish(user));
    }

    @GetMapping
    public ResponseEntity<List<User>> filterUsers(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String name) {

        List<User> users = userService.filterUsers(email, name);
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> delete(@PathVariable String email) {
        userService.delete(email);
        return ResponseEntity.noContent().build();
    }
}
