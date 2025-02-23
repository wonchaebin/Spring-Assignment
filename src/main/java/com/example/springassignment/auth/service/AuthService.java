package com.example.springassignment.auth.service;

import com.example.springassignment.auth.dto.AuthLoginRequestDto;
import com.example.springassignment.auth.dto.AuthLoginResponseDto;
import com.example.springassignment.auth.dto.AuthSignupRequestDto;
import com.example.springassignment.member.entity.Member;
import com.example.springassignment.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    @Transactional
    public void signup(AuthSignupRequestDto dto) {
        Member member = new Member(dto.getUsername(), dto.getEmail());
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public AuthLoginResponseDto login(AuthLoginRequestDto dto) {
        Member member = memberRepository.findByEmail(dto.getEmail()).orElseThrow(
                () -> new IllegalStateException("존제하지 않는 사용자입니다.")
        );
        return new AuthLoginResponseDto(member.getId());
    }
}
