package com.example.studentmanagement.controller;


import com.example.studentmanagement.entity.Lesson;
import com.example.studentmanagement.enums.UserType;
import com.example.studentmanagement.repository.UserRepository;
import com.example.studentmanagement.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LessonController {


    private final LessonService lessonService;

    private final UserRepository userRepository;

    @GetMapping("/lessons")
    public String LessonPage(ModelMap modelMap) {
        modelMap.addAttribute("lessons", lessonService.findAll());
        return "lessons";
    }

    @GetMapping("/lessons/add")
    public String addLessonsPage(ModelMap modelMap) {
        modelMap.addAttribute("teachers",   userRepository.findByUserType(  UserType.TEACHER));
        return "addLesson";
    }

    @PostMapping("/lessons/add")
    public String addLesson(@ModelAttribute Lesson lesson) {
        lessonService.save(lesson);
        return "redirect:/lessons";
    }

    @GetMapping("/lessons/delete/{id}")
    public String deleteLessons(@PathVariable("id") int id) {
        lessonService.deleteById(id);
        return "redirect:/lessons";
    }

    @GetMapping("/lessons/update/{id}")
    public String updateLessonPage(@PathVariable("id") int id, ModelMap modelMap) {
        modelMap.addAttribute("lesson", lessonService.getById(id));
        return "updateLesson";
    }

    @PostMapping("/lessons/update")
    public String updateLesson(@ModelAttribute Lesson lesson) {
        lessonService.save(lesson);
        return "redirect:/lessons";
    }
}