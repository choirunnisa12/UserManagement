package com.example.UserManagementt.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Builder
public class User implements Serializable {
    private static  final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    @NotBlank(message = "Name cannot be null")
    @Size(min = 2, message = "Name should have 2 characters")
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "email is mandatory")
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "birthDate")
    @NotBlank(message = "you have to fill your birthdate  ")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotBlank(message = "password is mandatory")
    private String password;

}
