package com.example.springassignment.member.dto;

import lombok.Getter;

@Getter
public class MemberSaveResponseDto {

    private final Long id;
    private final String name;
    private final String email;

    public MemberSaveResponseDto(Long id, String name, String email, Long memberId, String memberEmail) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
