package com.example.UserManagementt.service;

import com.example.UserManagementt.entity.User;

import java.util.List;

public interface UserService {
    User create(User request);
    List<User> getAll();
    User getById(long id);
    User update(User request);
    void delete(long id);
    List<User>searchByName(String name);
}
