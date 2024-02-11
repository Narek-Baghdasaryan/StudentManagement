package com.example.studentmanagement.service;

import com.example.studentmanagement.entity.Message;

import java.util.List;

public interface MessageService {

    Message save(Message message);

    List<Message> findByToId(int id);

}
