package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.Message;
import com.example.studentmanagement.service.MessageService;
import com.example.studentmanagement.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final StudentService studentService;

    @GetMapping("/sendMessage/{id}")
    public String sendMessagePage(@PathVariable("id") int id, ModelMap modelMap){
        modelMap.addAttribute("toId" ,id);
        return "sendMessage";
    }

    @PostMapping("/sendMessage/forward")
    public String forward(@ModelAttribute Message message){
        messageService.save(message);
        return "redirect:/students";
    }

    @GetMapping("/messages")
    public String messagesPage(@RequestParam ("id") int id, ModelMap modelMap){
        modelMap.addAttribute("messages", messageService.findByToId(id));
        return "messages";
    }

}
