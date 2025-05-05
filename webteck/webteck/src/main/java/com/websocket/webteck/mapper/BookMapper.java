package com.websocket.webteck.mapper;

import com.websocket.webteck.dto.BookDTO;
import com.websocket.webteck.model.Book;

public class BookMapper {
    public static BookDTO toDto(Book book){
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setPages(book.getPages());
        return dto;
    }

    public static Book toEntity(BookDTO bookDto){
        Book dto = new Book();
        dto.setId(bookDto.getId());
        dto.setTitle(bookDto.getTitle());
        dto.setAuthor(bookDto.getAuthor());
        dto.setPages(bookDto.getPages());
        return dto;
    }
}
