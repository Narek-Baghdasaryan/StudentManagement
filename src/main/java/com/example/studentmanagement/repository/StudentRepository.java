package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.Lesson;
import com.example.studentmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<User, Integer> {

}
