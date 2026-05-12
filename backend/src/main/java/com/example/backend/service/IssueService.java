package com.example.backend.service;



import com.example.backend.dto.IssueRequestDto;
import com.example.backend.entity.Book;
import com.example.backend.entity.IssueRecord;
import com.example.backend.entity.Member;
import com.example.backend.exception.BookNotAvailableException;
import com.example.backend.exception.IssueLimitExceededException;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.repository.BookRepository;
import com.example.backend.repository.IssueRecordRepository;
import com.example.backend.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class IssueService {

    private final IssueRecordRepository issueRecordRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public IssueService(IssueRecordRepository issueRecordRepository,
                        BookRepository bookRepository,
                        MemberRepository memberRepository) {

        this.issueRecordRepository = issueRecordRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    public IssueRecord issueBook(IssueRequestDto requestDto) {

        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found"));

        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Member not found"));

        if (!book.isAvailable()) {
            throw new BookNotAvailableException(
                    "Book is already issued");
        }

        long activeBooks =
                issueRecordRepository
                        .countByMemberMemberIdAndReturnDateIsNull(
                                member.getMemberId());

        if (activeBooks >= 3) {
            throw new IssueLimitExceededException(
                    "Member already has 3 books issued");
        }

        IssueRecord issueRecord = new IssueRecord();

        issueRecord.setBook(book);
        issueRecord.setMember(member);
        issueRecord.setIssueDate(LocalDate.now());
        issueRecord.setReturnDate(null);

        book.setAvailable(false);

        bookRepository.save(book);

        return issueRecordRepository.save(issueRecord);
    }
}
