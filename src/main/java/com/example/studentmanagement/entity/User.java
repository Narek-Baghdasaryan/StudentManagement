package com.example.studentmanagement.entity;

import com.example.studentmanagement.enums.UserType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String surname;

    private String email;

    private String password;

    private String picName;

    @ManyToOne
    private Lesson lesson;

    @Enumerated(EnumType.STRING)
    private UserType userType;
}