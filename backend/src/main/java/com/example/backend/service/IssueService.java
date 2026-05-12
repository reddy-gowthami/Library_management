package com.example.backend.service;

import com.example.backend.dto.IssueRequestDto;
import com.example.backend.entity.Book;
import com.example.backend.entity.IssueRecord;
import com.example.backend.entity.User;
import com.example.backend.exception.BookNotAvailableException;
import com.example.backend.exception.IssueLimitExceededException;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.repository.BookRepository;
import com.example.backend.repository.IssueRecordRepository;
import com.example.backend.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class IssueService {

    private final IssueRecordRepository issueRecordRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public IssueService(IssueRecordRepository issueRecordRepository,
                        BookRepository bookRepository,
                        UserRepository userRepository) {

        this.issueRecordRepository = issueRecordRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public IssueRecord issueBook(IssueRequestDto requestDto) {

        // Find Book
        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found"));

        // Find User
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        // Check Book Availability
        if (!book.isAvailability()) {
            throw new BookNotAvailableException(
                    "Book is already issued");
        }

        // Check Active Issued Books Count
        long activeBooks =
                issueRecordRepository
                        .countByUserIdAndReturnDateIsNull(
                                user.getId());

        // Max 3 Books Rule
        if (activeBooks >= 3) {
            throw new IssueLimitExceededException(
                    "User already has 3 books issued");
        }

        // Create Issue Record
        IssueRecord issueRecord = new IssueRecord();

        issueRecord.setBook(book);
        issueRecord.setUser(user);
        issueRecord.setIssueDate(LocalDate.now());
        issueRecord.setReturnDate(null);

        // Mark Book Unavailable
        book.setAvailability(false);

        // Save Updated Book
        bookRepository.save(book);

        // Save Issue Record
        return issueRecordRepository.save(issueRecord);
    }
}