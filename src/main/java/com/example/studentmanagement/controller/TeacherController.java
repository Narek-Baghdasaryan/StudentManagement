package com.example.studentmanagement.controller;


import com.example.studentmanagement.entity.User;
import com.example.studentmanagement.enums.UserType;
import com.example.studentmanagement.repository.LessonRepository;
import com.example.studentmanagement.repository.UserRepository;
import com.example.studentmanagement.service.LessonService;
import com.example.studentmanagement.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class TeacherController {


    private final TeacherService teacherService;

    private final LessonService lessonService;

    @GetMapping("/teachers")
    public String TeacherPage(ModelMap modelMap) {
        modelMap.addAttribute("teachers", teacherService.findByUserType(UserType.TEACHER));
        return "teachers";
    }

    @GetMapping("/teachers/add")
    public String addTeachersPage(@RequestParam(value = "msg", required = false) String msg, ModelMap modelMap) {
        if (msg != null) {
            modelMap.addAttribute("msg", msg);
        }
        modelMap.addAttribute("lessons", lessonService.findAll());
        return "addTeacher";
    }

    @PostMapping("/teachers/add")
    public String addTeacher(@ModelAttribute User user,
                             @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        Optional<User> byEmail = teacherService.findByEmail(user.getEmail());
        if (byEmail != null) {
            teacherService.save(user, multipartFile);
            return "redirect:/teachers/add?msg=teacher registered";
        } else {
            return "redirect:/teachers/add?msg=email already is use";
        }
    }

    @GetMapping("/teachers/delete/{id}")
    public String deleteStudent(@PathVariable("id") int id) {
        teacherService.deleteById(id);
        return "redirect:/teachers";
    }

    @GetMapping("/teachers/update/{id}")
    public String updateTeacherPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<User> byId = teacherService.findById(id);
        if (byId.isPresent()) {
            modelMap.addAttribute("user", byId.get());
            modelMap.addAttribute("lessons", lessonService.findAll());
        } else {
            return "redirect:/teachers";
        }
        return "updateTeacher";
    }

    @PostMapping("/teachers/update")
    public String updateTeacher(@ModelAttribute User user,
                                @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        teacherService.update(user,multipartFile);
        return "redirect:/teachers";
    }

    @GetMapping("/teachers/image/delete")
    public String deleteTeacherImage(@RequestParam("id") int id) {
        Optional<User> user = teacherService.findById(id);
        if (user.isEmpty()) {
            return "redirect:/teachers";
        } else {
            teacherService.deleteImage(id);
        }
        return "redirect:/teachers/update/" + user.get().getId();

    }
}