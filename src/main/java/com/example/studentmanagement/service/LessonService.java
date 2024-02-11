package com.example.studentmanagement.service;

import com.example.studentmanagement.entity.Lesson;

import java.util.List;

public interface LessonService {

    List<Lesson> findAll();

    Lesson save(Lesson lesson);

    void deleteById(int id);

    Lesson getById(int id);

}
