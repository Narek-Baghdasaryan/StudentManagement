package com.example.studentmanagement.service;

import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.enums.UserType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface TeacherService {

    List<User> findByUserType(UserType userType);

    Optional<User> findByEmail(String email);

    User save(User user, MultipartFile multipartFile) throws IOException;

    void deleteById(int id);

    Optional<User> findById(int id);

    User update(User user,MultipartFile multipartFile) throws IOException;

    void deleteImage(int id);

}
