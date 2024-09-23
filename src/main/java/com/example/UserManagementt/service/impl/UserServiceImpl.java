package com.example.UserManagementt.service.impl;

import com.example.UserManagementt.entity.User;
import com.example.UserManagementt.exception.UserNotFoundException;
import com.example.UserManagementt.repository.UserRepository;
import com.example.UserManagementt.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User create(User request) {
        return userRepository.save(request);
    }

    @Override
    public User getById(long id) {
        logger.info("Fetching user from database with id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " is not found"));
    }

    @Override
    public User update(long id, User request) {
        User existingUser = getById(id);
        existingUser.setName(request.getName());
        existingUser.setEmail(request.getEmail());
        existingUser.setBirthDate(request.getBirthDate());
        existingUser.setPassword(request.getPassword());
        return userRepository.save(existingUser);
    }

    @CacheEvict(value = "users", key = "#id")
    @Override
    public void delete(long id) {
        User existingUser = getById(id);
        userRepository.deleteById(existingUser.getId());
        logger.info("User with id: {} has been deleted", id);
    }

    @Override
    @Cacheable(value = "users", key = "#page + '-' + #size + '-' + #sortBy + '-' + #direction")
    public Page<User> getAll(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by("ASC".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return userRepository.findAll(pageable);
    }

    @Override
    public List<User> searchByName(String name) {
        return userRepository.findByNameContaining(name);
    }
}
