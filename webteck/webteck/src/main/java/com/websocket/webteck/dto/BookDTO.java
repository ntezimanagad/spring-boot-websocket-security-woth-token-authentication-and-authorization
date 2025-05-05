package com.websocket.webteck.dto;

public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private int pages;
    public BookDTO() {
    }
    public BookDTO(String title, String author, int pages) {
        this.title = title;
        this.author = author;
        this.pages = pages;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public int getPages() {
        return pages;
    }
    public void setPages(int pages) {
        this.pages = pages;
    }
    
}
