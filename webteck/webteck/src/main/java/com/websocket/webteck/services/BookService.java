package com.websocket.webteck.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.websocket.webteck.dto.BookDTO;
import com.websocket.webteck.mapper.BookMapper;
import com.websocket.webteck.model.Book;
import com.websocket.webteck.repository.BookRepository;

@Service
public class BookService {
    @Autowired
    private EmailService emailService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserService userService;

    public List<BookDTO> getAllBook(){
        return bookRepository.findAll()
            .stream()
            .map(BookMapper::toDto)
            .collect(Collectors.toList());
    }
    public BookDTO getBookById(Long id){
        Book book = bookRepository.findById(id)
            .orElseThrow(()-> new RuntimeException("User Not Found"));
        return BookMapper.toDto(book);
    }
    public Page<BookDTO> getByPage(org.springframework.data.domain.Pageable pageable){
        return bookRepository.findAll(pageable)
            .map(BookMapper::toDto);
    }
    public Page<BookDTO> getBookByPage(String keyword,Pageable pageable){
        Page<Book> pages;
        if (keyword != null && !keyword.trim().isEmpty()) {
            pages = bookRepository.findByTitleContainingIgnoreCase(keyword, pageable);
        }else{
            pages = bookRepository.findAll(pageable);
        }
        return pages.map(BookMapper::toDto);
    }
    public void createUser(BookDTO bookDTO){
        Optional<Book> optionalBook = bookRepository.findByTitle(bookDTO.getTitle());
        if (optionalBook.isPresent()) {
            throw new RuntimeException("User Already Exist");
        }
        Book book = BookMapper.toEntity(bookDTO);
        String subject = "New Book Added";
        String text = "New Book Called " + bookDTO.getTitle() +" added";
        bookRepository.save(book);
        userService.getAllEmail().forEach(email ->
            emailService.sendEmail(email, subject, text)
        );
    }
    
}
