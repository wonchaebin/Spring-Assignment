package com.example.springassignment.todo.dto;

import lombok.Getter;

@Getter
public class TodoSaveResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final Long memberId;
    private final String memberEmail;

    public TodoSaveResponseDto(Long id, String title, String content, Long memberId, String memberEmail) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.memberEmail = memberEmail;
    }
}
