package com.practice.librarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.librarymanagement.entity.Book;
import java.util.List;
import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book,Long>{
    List<Book> findByAuthor(String author);

    Optional<Book> findByBookTitle(String bookTitle);

}
