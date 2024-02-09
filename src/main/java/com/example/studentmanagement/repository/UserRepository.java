package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.Lesson;
import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    List<User> findByUserType(UserType typeUser);
    Optional<User> findByEmail(String email);
}

