package com.example.UserManagementt.controller;

import com.example.UserManagementt.entity.User;
import com.example.UserManagementt.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User request) {
        User saveUser = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveUser);
    }

    @GetMapping(path = "/{id}")
    public User getById(@PathVariable long id){
        return userService.getById(id);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id){
        userService.delete(id);
    }
    @GetMapping
    public ResponseEntity<Page<User>> getAll(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "id")String sortBy,
            @RequestParam(defaultValue = "asc")String direction){
        Page<User> users = userService.getAll(page, size, sortBy, direction);
                return ResponseEntity.ok(users);
    }
}
