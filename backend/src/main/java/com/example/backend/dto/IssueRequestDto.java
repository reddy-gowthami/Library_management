package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueRequestDto {

    private Long bookId;

    private Long memberId;
}