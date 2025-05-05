package com.websocket.webteck.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.websocket.webteck.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
    Optional<Book> findByTitle(String title);
    Page<Book> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
}
