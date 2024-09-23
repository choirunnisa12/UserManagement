package com.example.UserManagementt.controller;

import com.example.UserManagementt.entity.User;
import com.example.UserManagementt.service.UserService;
import com.example.UserManagementt.service.impl.RateLimiterService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private RateLimiterService rateLimiterService;

    @Async
    @GetMapping("/resource")
    public CompletableFuture<ResponseEntity<String>> getResource() {
        return CompletableFuture.supplyAsync(()-> {
        if (rateLimiterService.isRateLimited()) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many requests");
        }
        return ResponseEntity.ok("Resource content");
    });
    }

    @Async
    @PostMapping
    public CompletableFuture<ResponseEntity<?>> create(@Valid @RequestBody User request, BindingResult result) {
        return CompletableFuture.supplyAsync(()-> {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        try {
            User saveUser = userService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(saveUser);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user");
        }
        });
    }

    @Async
    @GetMapping(path = "/{id}")
    public CompletableFuture<ResponseEntity<User>> getById(@PathVariable long id) {
       return CompletableFuture.supplyAsync(()-> {
        User user = userService.getById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }});
    }
    @Async
    @PutMapping(path = "/{id}")
    public CompletableFuture<ResponseEntity<User>> update(@PathVariable long id, @Valid @RequestBody User request){
       return CompletableFuture.supplyAsync(() -> {
           User updateUser = userService.update(id, request);
           if (updateUser != null) {
               return ResponseEntity.ok(updateUser);
           } else {
               return ResponseEntity.notFound().build();
           }
       });
    }
    @Async
    @DeleteMapping(path = "/{id}")
    public CompletableFuture<ResponseEntity<?>> delete(@PathVariable long id) {
        return CompletableFuture.supplyAsync(()->{
        if (userService.getById(id) != null){
            userService.delete(id);
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.noContent().build();
        }});
    }

    @Async
    @GetMapping
    public CompletableFuture<ResponseEntity<Page<User>>> getAll(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "id")String sortBy,
            @RequestParam(defaultValue = "asc")String direction) {

        return CompletableFuture.supplyAsync(() -> {
            Page<User> users = userService.getAll(page, size, sortBy, direction);
            return ResponseEntity.ok(users);
        });
    }

    @Async
    @GetMapping("/search")
    public CompletableFuture<ResponseEntity<List<User>>>searchUsers(@RequestParam String name){
       return CompletableFuture.supplyAsync(()->{
        List<User> users = userService.searchByName(name);
        return ResponseEntity.ok(users);
    });
    }
}
