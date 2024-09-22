package com.example.UserManagementt.controller;

import com.example.UserManagementt.entity.User;
import com.example.UserManagementt.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<User> getById(@PathVariable long id) {
        User user = userService.getById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<User>>searchUsers(@RequestParam String name){
        List<User> users = userService.searchByName(name);
        return ResponseEntity.ok(users);
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/{id}")
    public User update(@PathVariable User request){
        return userService.update(request);
    }
    @GetMapping
    public ResponseEntity<Page<User>> getAll(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size){
        Page<User> users = userService.getAll(page,size);
            return ResponseEntity.ok(users);

    }
}
