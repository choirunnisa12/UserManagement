package com.example.UserManagementt.service;

import com.example.UserManagementt.dto.*;
import com.example.UserManagementt.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    UserResponseDto create(UserDTO request);
    Page<UserResponseDto> getAll(int page, int size, String sortBy, String direction);
    UserResponseDto getById(long id);
    UserResponseDto update(long id, UserDTO request);
    void delete(long id);
    List<UserResponseDto>searchByName(String name);

}
