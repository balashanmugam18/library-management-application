package com.librarymanagement.application.model;

import com.librarymanagement.application.libEnum.Category;

import jakarta.validation.constraints.Min;

public class BookRequest {
    
    public String title;
    public String isbn;
    public String author;
    public Category category;
    
    @Min(value = 1, message = "Minimum 1 copy must be submitted")
    public Integer availableCopies;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(Integer availableCopies) {
        this.availableCopies = availableCopies;
    }

}
