package com.example.studentmanagement.service.impl;

import com.example.studentmanagement.entity.Message;
import com.example.studentmanagement.repository.MessageRepository;
import com.example.studentmanagement.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public Message save(Message massage) {
        return messageRepository.save(massage);
    }

    @Override
    public List<Message> findByToId(int id) {
        return messageRepository.findByToId(id);
    }
}