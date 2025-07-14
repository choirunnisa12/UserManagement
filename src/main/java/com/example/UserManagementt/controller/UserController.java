package com.example.UserManagementt.controller;

import com.example.UserManagementt.dto.UserDTO;
import com.example.UserManagementt.dto.UserResponseDto;
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
    public CompletableFuture<ResponseEntity<?>> create(@Valid @RequestBody UserDTO request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                UserResponseDto savedUser = userService.create(request);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user: " + e.getMessage());
            }
        });
    }


    @Async
    @GetMapping(path = "/{id}")
    public CompletableFuture<ResponseEntity<UserResponseDto>> getById(@PathVariable long id) {
       return CompletableFuture.supplyAsync(()-> {
        try {
            UserResponseDto user = userService.getById(id);
            return ResponseEntity.ok(user);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }});
    }

    @Async
    @PutMapping(path = "/{id}")
    public CompletableFuture<ResponseEntity<UserResponseDto>> update(@PathVariable long id, @Valid @RequestBody UserDTO request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                UserResponseDto updateUser = userService.update(id, request);
                return ResponseEntity.ok(updateUser);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        });
    }

    @Async
    @DeleteMapping(path = "/{id}")
    public CompletableFuture<ResponseEntity<Void>> delete(@PathVariable long id) {
        return CompletableFuture.supplyAsync(()->{
        try {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
        });
    }

    @Async
    @GetMapping
    public CompletableFuture<ResponseEntity<Page<UserResponseDto>>> getAll(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(defaultValue = "id")String sortBy,
            @RequestParam(defaultValue = "asc")String direction) {

        return CompletableFuture.supplyAsync(() -> {
            Page<UserResponseDto> users = userService.getAll(page, size, sortBy, direction);
            return ResponseEntity.ok(users);
        });
    }

    @Async
    @GetMapping("/search")
    public CompletableFuture<ResponseEntity<List<UserResponseDto>>>searchUsers(@RequestParam String name){
       return CompletableFuture.supplyAsync(()->{
        List<UserResponseDto> users = userService.searchByName(name);
        return ResponseEntity.ok(users);
    });
    }
}
