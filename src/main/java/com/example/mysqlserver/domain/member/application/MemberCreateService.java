package com.example.mysqlserver.domain.member.application;

import com.example.mysqlserver.domain.member.domain.Member;
import com.example.mysqlserver.domain.member.domain.MemberNicknameHistory;
import com.example.mysqlserver.domain.member.domain.MemberNicknameHistoryRepository;
import com.example.mysqlserver.domain.member.domain.MemberRepository;
import com.example.mysqlserver.domain.member.dto.MemberCreateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class MemberCreateService {

    private final MemberRepository memberRepository;

    private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    public Member create(MemberCreateCommand memberCreateCommand) {
        var member = Member.builder()
                .nickname(memberCreateCommand.nickname())
                .email(memberCreateCommand.email())
                .birthday(memberCreateCommand.birthday())
                .createdAt(LocalDateTime.now())
                .build();
        Member newMember = memberRepository.save(member);
        createMemberNicknameHistory(newMember);
        return newMember;
    }

    public void updateNickname(Long id, String nickname) {
        Member member = memberRepository.findById(id).orElseThrow();
        member.updateNickname(nickname);
        memberRepository.update(member);
        createMemberNicknameHistory(member);
    }

    private void createMemberNicknameHistory(Member member) {
        MemberNicknameHistory memberNicknameHistory = MemberNicknameHistory.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .createdAt(LocalDateTime.now())
                .build();
        memberNicknameHistoryRepository.save(memberNicknameHistory);
    }

}
