package com.example.studentmanagement.controller;

import com.example.studentmanagement.enums.UserType;
import com.example.studentmanagement.repository.StudentRepository;
import com.example.studentmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/students")
    public String StudentPage(ModelMap modelMap){
       modelMap.addAttribute("students", userRepository.findByUserType(UserType.STUDENT));
        return "students";
    }

}
