package com.example.backend.repository;

import com.example.backend.entity.IssueRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRecordRepository
        extends JpaRepository<IssueRecord, Long> {

    long countByUserIdAndReturnDateIsNull(Long userId);

    boolean existsByBookBookIdAndReturnDateIsNull(Long bookId);
}