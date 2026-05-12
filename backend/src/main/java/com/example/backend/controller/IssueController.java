package com.example.backend.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.IssueRequestDto;
import com.example.backend.entity.IssueRecord;
import com.example.backend.service.IssueService;

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
