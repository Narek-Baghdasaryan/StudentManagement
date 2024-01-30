package com.example.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Entity
@Data
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private Date duration;
    private int price;
    private Date start_date;
    @ManyToOne
    private User teacher_id;
}
