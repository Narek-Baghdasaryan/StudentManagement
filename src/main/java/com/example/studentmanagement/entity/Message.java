package com.example.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;

@Entity
@Data
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String massage;

    @ManyToOne
    private User from;

    @ManyToOne
    private User to;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateTime;

}