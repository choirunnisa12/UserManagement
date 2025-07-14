package com.example.UserManagementt.service.impl;

import com.example.UserManagementt.dto.UserDTO;
import com.example.UserManagementt.dto.UserResponseDto;
import com.example.UserManagementt.entity.User;
import com.example.UserManagementt.exception.UserNotFoundException;
import com.example.UserManagementt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Data bantu contoh
    private UserDTO getSampleDto() {
        return new UserDTO("Example User", "example@email.com", "2000-01-01", "secret");
    }

    private User getSampleUser() {
        return User.builder()
                .id(1L)
                .name("Example User")
                .email("example@email.com")
                .birthDate(LocalDate.of(2000, 1, 1))
                .password("secret")
                .build();
    }

    @Test
    void testCreate() {
        UserDTO dto = getSampleDto();
        User savedUser = getSampleUser();

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponseDto result = userService.create(dto);

        assertNotNull(result);
        assertEquals("Example User", result.getName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetById() {
        User user = getSampleUser();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponseDto result = userService.getById(1L);

        assertEquals("Example User", result.getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetById_NotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getById(99L));
        verify(userRepository, times(1)).findById(99L);
    }

    @Test
    void testUpdate() {
        User existing = getSampleUser();
        UserDTO updateDto = new UserDTO("Updated User", "updated@email.com", "1999-01-01", "newpass");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.save(any(User.class))).thenReturn(existing);

        UserResponseDto result = userService.update(1L, updateDto);

        assertEquals("Updated User", result.getName());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testDelete() {
        User user = getSampleUser();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(1L);

        userService.delete(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void testSearchByName() {
        User user = getSampleUser();
        when(userRepository.findByNameContaining("Example")).thenReturn(List.of(user));

        List<UserResponseDto> result = userService.searchByName("Example");

        assertEquals(1, result.size());
        assertEquals("Example User", result.get(0).getName());
        verify(userRepository).findByNameContaining("Example");
    }

    @Test
    void testGetAll() {
        User user = getSampleUser();
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
        Page<User> page = new PageImpl<>(List.of(user), pageable, 1);

        when(userRepository.findAll(pageable)).thenReturn(page);

        Page<UserResponseDto> result = userService.getAll(0, 10, "id", "asc");

        assertEquals(1, result.getTotalElements());
        verify(userRepository).findAll(pageable);
    }
}
