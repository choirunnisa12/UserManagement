package com.example.UserManagementt.controller;

import com.example.UserManagementt.entity.User;
import com.example.UserManagementt.service.UserService;
import lombok.AllArgsConstructor;
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
    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }
    @GetMapping(path = "/{id}")
    public User getById(@PathVariable long id){
        return userService.getById(id);
    }
    @PutMapping(path = "/{id}")
    public User update(@PathVariable User request){
        return userService.update(request);
    }
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id){
        userService.delete(id);
    }
}
