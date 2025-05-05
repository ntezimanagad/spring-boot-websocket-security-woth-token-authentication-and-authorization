package com.websocket.webteck.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.websocket.webteck.dto.BookDTO;
import com.websocket.webteck.services.BookService;

@RestController
@RequestMapping(value = "/api/book")
@CrossOrigin("*")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @GetMapping(value = "/getAlBook")
    public ResponseEntity<List<BookDTO>> getAllBook(){
        return ResponseEntity.ok(bookService.getAllBook());
    }
    @GetMapping(value = "/getBookById")
    public ResponseEntity<?> getBookById(Long id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }
    @GetMapping(value = "/getByPage")
    public ResponseEntity<Page<BookDTO>> getByPage(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size
    ){
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, size);
        Page<BookDTO> book = bookService.getByPage(pageable);
        return ResponseEntity.ok(book);
    }
    @GetMapping(value = "/getBookByPage")
    public ResponseEntity<Page<BookDTO>> getBookByPage(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "title") String sortBy,
        @RequestParam(defaultValue = "asc") String direction,
        @RequestParam(required = false) String keyword
    ){
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, size, sort);
        Page<BookDTO> book = bookService.getBookByPage(keyword, pageable);
        return ResponseEntity.ok(book);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createBook(@RequestBody BookDTO bookDTO) {
        bookService.createUser(bookDTO);
        messagingTemplate.convertAndSend("/topic/books", bookDTO);
        return ResponseEntity.ok("created");
    }

}
