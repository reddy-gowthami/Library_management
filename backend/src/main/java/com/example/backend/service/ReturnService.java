package com.example.backend.service;


import com.example.backend.entity.Book;
import com.example.backend.entity.IssueRecord;
import com.example.backend.repository.BookRepository;
import com.example.backend.repository.IssueRecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReturnService {

    @Autowired
    private IssueRecordRepository issueRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    public String returnBook(Long issueId) {

        // Find issue record
        IssueRecord issueRecord = issueRecordRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue record not found"));

        // Check if already returned
        if (issueRecord.getReturnDate() != null) {
            throw new RuntimeException("Book already returned");
        }

        // Update return date
        issueRecord.setReturnDate(LocalDate.now());

        // Get associated book
        Book book = issueRecord.getBook();

        // Mark book as available
        book.setAvailability(true);

        // Save updates
        bookRepository.save(book);
        issueRecordRepository.save(issueRecord);

        return "Book returned successfully";
    }
}