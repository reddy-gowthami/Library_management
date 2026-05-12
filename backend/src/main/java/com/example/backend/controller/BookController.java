package com.example.backend.controller;

import com.example.backend.entity.Book;
import com.example.backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/available")
    public List<Book> getAvailableBooks() {
        return bookService.getAvailableBooks();
    }

    @GetMapping("/title/{title}")
    public List<Book> searchByTitle(@PathVariable String title) {
        return bookService.searchByTitle(title);
    }

    @GetMapping("/author/{author}")
    public List<Book> searchByAuthor(@PathVariable String author) {
        return bookService.searchByAuthor(author);
    }
}
