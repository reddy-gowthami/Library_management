package com.example.backend.controller;

import com.example.backend.service.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/returns")
public class ReturnController {

    @Autowired
    private ReturnService returnService;

    @PutMapping("/{issueId}")
    public ResponseEntity<String> returnBook(@PathVariable Long issueId) {

        String response = returnService.returnBook(issueId);

        return ResponseEntity.ok(response);
    }
}
