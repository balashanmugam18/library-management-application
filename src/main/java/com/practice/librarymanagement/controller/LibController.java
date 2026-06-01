package com.practice.librarymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.practice.librarymanagement.entity.Book;
import com.practice.librarymanagement.entity.TransferBook;
import com.practice.librarymanagement.model.BookRequest;
import com.practice.librarymanagement.service.LibService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/api")
@RestController
public class LibController {
    @Autowired
    private LibService libService;

    @GetMapping
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok("Backend working");
    }

    @GetMapping("/books/search/{name}")
    public ResponseEntity<?> getBooks(@PathVariable ("name")String name) {
        List<Book> books = libService.getByAuthor(name);
        return ResponseEntity.ok(books);
    }

    @PostMapping("books")
    public ResponseEntity<?> addBooks(@RequestBody BookRequest bookRequest) {
        Book responseBook = libService.addBook(bookRequest);
        return ResponseEntity.ok(responseBook);
    }

    @PostMapping("/transactions/borrow")
    public ResponseEntity<?> transferBook(@RequestParam String memberId, @RequestParam String bookTitle) {
        TransferBook responseBook = libService.transfer(memberId, bookTitle);
        return ResponseEntity.ok(responseBook);
    }
    
    
}
