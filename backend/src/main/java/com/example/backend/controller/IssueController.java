package com.example.backend.controller;

import com.example.backend.dto.IssueRequestDto;
import com.example.backend.entity.IssueRecord;
import com.example.backend.service.IssueService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @PostMapping("/issue")
    public ResponseEntity<IssueRecord> issueBook(
            @RequestBody IssueRequestDto requestDto) {

        return ResponseEntity.ok(
                issueService.issueBook(requestDto));
    }
}