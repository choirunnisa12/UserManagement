package com.example.UserManagementt.service;

import com.example.UserManagementt.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    User create(User request);
    Page<User> getAll(int page, int size, String sortBy, String direction);
    User getById(long id);
    User update(long id, User request);
    void delete(long id);
    List<User>searchByName(String name);
}
