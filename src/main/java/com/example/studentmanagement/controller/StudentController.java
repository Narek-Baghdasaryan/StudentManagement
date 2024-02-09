package com.example.studentmanagement.controller;


import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.enums.UserType;
import com.example.studentmanagement.repository.LessonRepository;
import com.example.studentmanagement.repository.UserRepository;
import com.example.studentmanagement.security.SpringUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
public class StudentController {

    @Value("${picture.upload.directory}")
    private String uploadDirectory;


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LessonRepository lessonRepository;

    @GetMapping("/students")
    public String StudentsPage(ModelMap modelMap, @AuthenticationPrincipal SpringUser springUser) {
        if(springUser != null){
            modelMap.addAttribute("user", springUser.getUser());
        }
        modelMap.addAttribute("students", userRepository.findByUserType(UserType.STUDENT));
        return "students";
    }

    @GetMapping("/students/add")
    public String addStudentsPage(@RequestParam(value = "msg", required = false) String msg, ModelMap modelMap) {
        if (msg != null){
            modelMap.addAttribute("msg", msg);
        }
        modelMap.addAttribute("lessons", lessonRepository.findAll());
        return "addStudents";
    }
    @PostMapping("/students/add")
    public String addStudent(@ModelAttribute User user,
                             @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String picName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File file = new File(uploadDirectory, picName);
            multipartFile.transferTo(file);
            user.setPicName(picName);

        }
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail.isEmpty()){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return "redirect:/students/add?msg=Student Registered";
        }else {
            return "redirect:/students/add?msg=email already is use";
        }
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") int id) {
        userRepository.deleteById(id);
        return "redirect:/students";
    }
    @GetMapping("/students/update/{id}")
    public String updateStudentPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            modelMap.addAttribute("user", byId.get());
            modelMap.addAttribute("lessons", lessonRepository.findAll());
        } else {
            return "redirect:/students";
        }
        return "updateStudent";
    }
    @PostMapping("/students/update")
    public String updateStudent(@ModelAttribute User user,
                                @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String picName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File file = new File(uploadDirectory, picName);
            multipartFile.transferTo(file);
            user.setPicName(picName);
        }else {
            Optional<User> fromDB = userRepository.findById(user.getId());
            user.setPicName(fromDB.get().getPicName());
        }
        userRepository.save(user);
        return "redirect:/students";
    }
    @GetMapping("/students/image/delete")
    public String deleteStudentsImage(@RequestParam("id") int id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isEmpty()) {
            return "redirect:/students";
        } else {
            User user = byId.get();
            String picName = user.getPicName();
            if (picName != null) {
                user.setPicName(null);
                userRepository.save(user);
                File file = new File(uploadDirectory, picName);
                if (file.exists()) {
                    file.delete();
                }
            }
            return "redirect:/students/update/" + user.getId();
        }
    }
}