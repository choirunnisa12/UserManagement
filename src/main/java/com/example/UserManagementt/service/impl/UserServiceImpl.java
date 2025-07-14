package com.example.UserManagementt.service.impl;

import com.example.UserManagementt.dto.UserDTO;
import com.example.UserManagementt.dto.UserResponseDto;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Async
    @Override
    public UserResponseDto create(UserDTO request) {
        logger.info("Creating user: {}", request.getName());
        User user = mapToEntity(request);
        User savedUser = userRepository.save(user);
        return mapToResponseDto(savedUser);
    }

    @Override
    public UserResponseDto getById(long id) {
        logger.info("Fetching user from database with id: {}", id);
        User user =  userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " is not found"));
    return mapToResponseDto(user);
    }

    @Override
    public UserResponseDto update(long id, UserDTO request) {
        logger.info("updating user with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User with id " + id + " is not found"));
        return mapToResponseDto(user);
    }

    @CacheEvict(value = "users", key = "#id")
    @Override
    public void delete(long id) {
        logger.info("Deleting user with id: {}", id);
        User existingUser = userRepository.findById(id)
                        .orElseThrow(()-> new  UserNotFoundException("User with id " + id + "is not found"));
        userRepository.deleteById(existingUser.getId());
        logger.info("User with id: {} has been deleted", id);
    }

    @Override
    @Cacheable(value = "users", key = "#page + '-' + #size + '-' + #sortBy + '-' + #direction")
    public Page<UserResponseDto> getAll(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by("ASC".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<User> users = userRepository.findAll(pageable);
        return users.map(this::mapToResponseDto);
    }

    @Override
    public List<UserResponseDto> searchByName(String name) {
        logger.info("search by name : {}"+ name);
        List<User> users = userRepository.findByNameContaining(name);
        return users.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    private User mapToEntity(UserDTO dto){
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .birthDate(LocalDate.parse(dto.getBirthDate()))
                .password(dto.getPassword())
                .build();
    }

    private UserResponseDto mapToResponseDto(User user){
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setBirthDate(user.getBirthDate().toString());
        return dto;
    }
}
