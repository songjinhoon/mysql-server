package com.example.mysqlserver.domain.member.application;


import com.example.mysqlserver.domain.member.domain.Member;
import com.example.mysqlserver.domain.member.domain.MemberNicknameHistory;
import com.example.mysqlserver.domain.member.domain.MemberNicknameHistoryRepository;
import com.example.mysqlserver.domain.member.domain.MemberRepository;
import com.example.mysqlserver.domain.member.dto.MemberDto;
import com.example.mysqlserver.domain.member.dto.MemberNicknameHistoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberReadService {

    private final MemberRepository memberRepository;

    private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    public MemberDto read(Long id) {
        Member member = memberRepository.findById(id).orElseThrow();
        return toDto(member);
    }

    public List<MemberNicknameHistoryDto> readMemberNicknameHistory(Long memberId) {
        List<MemberNicknameHistory> memberNicknameHistories = memberNicknameHistoryRepository.findAllByMemberId(memberId);
        return memberNicknameHistories.stream().map(this::toDto).toList();
    }

    public MemberDto toDto(Member member) {
        return new MemberDto(member.getId(), member.getEmail(), member.getNickname(), member.getBirthday());
    }

    private MemberNicknameHistoryDto toDto(MemberNicknameHistory memberNicknameHistory) {
        return new MemberNicknameHistoryDto(memberNicknameHistory.getMemberId(), memberNicknameHistory.getNickname(), memberNicknameHistory.getCreatedAt());
    }

}
