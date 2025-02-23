package com.example.springassignment.member.service;

import com.example.springassignment.member.dto.MemberResponseDto;
import com.example.springassignment.member.dto.MemberSaveRequestDto;
import com.example.springassignment.member.dto.MemberSaveResponseDto;
import com.example.springassignment.member.dto.MemberUpdateRequestDto;
import com.example.springassignment.member.entity.Member;
import com.example.springassignment.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findAll() {
        List<Member> members = memberRepository.findAll();
        return members.stream().map(member -> new MemberResponseDto(member.getId(), member.getName(), member.getEmail())).toList();
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findById(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalStateException(("사용자가 존재하지 않습니다.")));
                return new MemberResponseDto(
                        member.getId(),
                        member.getName(),
                        member.getEmail()
                );
    }

    @Transactional
    public void update(Long memberId, MemberUpdateRequestDto dto) {
        Member member = memberRepository.findById(memberId).orElseThrow(
                ()-> new IllegalStateException("사용자를 찾을 수 없슫니다."));
        member.update(dto.getName(), dto.getEmail());
    }

    @Transactional
    public void deleteById(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new IllegalStateException("사용자를 찾을 수 없습니다.");
        }
        memberRepository.deleteById(memberId);
    }
}
