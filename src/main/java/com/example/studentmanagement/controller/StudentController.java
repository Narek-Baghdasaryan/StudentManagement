package com.example.studentmanagement.controller;


import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.enums.UserType;
import com.example.studentmanagement.service.LessonService;
import com.example.studentmanagement.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class StudentController {



    private final StudentService studentService;

    private final LessonService lessonService;

    @GetMapping("/students")
    public String StudentsPage(ModelMap modelMap) {
        modelMap.addAttribute("students", studentService.findByUserType(UserType.STUDENT));
        return "students";
    }

    @GetMapping("/students/add")
    public String addStudentsPage(@RequestParam(value = "msg", required = false) String msg, ModelMap modelMap) {
        if (msg != null) {
            modelMap.addAttribute("msg", msg);
        }
        modelMap.addAttribute("lessons", lessonService.findAll());
        return "addStudents";
    }

    @PostMapping("/students/add")
    public String addStudent(@ModelAttribute User user,
                             @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        if (studentService.findByEmail(user.getEmail())!=null) {
            studentService.save(user, multipartFile);
            return "redirect:/students/add?msg=student registered";
        } else {
            return "redirect:/students/add?msg=email already is use";
        }
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") int id) {
        studentService.deleteById(id);
        return "redirect:/students";
    }

    @GetMapping("/students/update/{id}")
    public String updateStudentPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<User> byId = studentService.findById(id);
        if (byId.isPresent()) {
            modelMap.addAttribute("user", byId.get());
            modelMap.addAttribute("lessons", lessonService.findAll());
        } else {
            return "redirect:/students";
        }
        return "updateStudent";
    }

    @PostMapping("/students/update")
    public String updateStudent(@ModelAttribute User user,
                                @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        studentService.update(user, multipartFile);
        return "redirect:/students";
    }

    @GetMapping("/students/image/delete")
    public String deleteStudentsImage(@RequestParam("id") int id) {
        Optional<User> byId = (studentService.findById(id));
        if (byId != null) {
            return "redirect:/students";
        } else {
            studentService.deleteImage(id);
        }
        return "redirect:/students/update/" + id;
    }
}