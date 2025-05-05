package com.websocket.webteck.controller;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.websocket.webteck.dto.BookDTO;

@Controller
@CrossOrigin("*")
public class BookNotificationController {

    @MessageMapping("/addBook")
    @SendTo("/topic/books") 
    public BookDTO notifyBook(BookDTO book) {
        return book;
    }
}
