package com.example.UserManagementt.service.impl;

import com.example.UserManagementt.entity.User;
import com.example.UserManagementt.repository.UserRepository;
import com.example.UserManagementt.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User create(User request) {
        return userRepository.saveAndFlush(request);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(long id) {
        Optional<User>user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public User update(User request) {
        this.getById(request.getId());
        return userRepository.save(request);
    }

    @Override
    public void delete(long id) {
        this.getById(id);
        userRepository.deleteById(id);
    }

    @Override
    public List<User> searchByName(String name) {
        return userRepository.findByNameContaining(name);
    }
}
