package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.entity.IssueRecord;

public interface IssueRecordRepository 
        extends JpaRepository<IssueRecord, Long> {

    long countByMemberMemberIdAndReturnDateIsNull(Long memberId);

    boolean existsByBookBookIdAndReturnDateIsNull(Long bookId);
}