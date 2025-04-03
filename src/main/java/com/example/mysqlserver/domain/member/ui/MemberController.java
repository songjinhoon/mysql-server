package com.example.mysqlserver.domain.member.ui;

import com.example.mysqlserver.domain.member.application.MemberCreateService;
import com.example.mysqlserver.domain.member.application.MemberReadService;
import com.example.mysqlserver.domain.member.domain.Member;
import com.example.mysqlserver.domain.member.dto.MemberCreateCommand;
import com.example.mysqlserver.domain.member.dto.MemberDto;
import com.example.mysqlserver.domain.member.dto.MemberNicknameHistoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(value = "/members")
@RestController
public class MemberController {

    private final MemberCreateService memberCreateService;

    private final MemberReadService memberReadService;

    @GetMapping("/{id}")
    public MemberDto read(@PathVariable Long id) {
        return memberReadService.read(id);
    }

    @GetMapping("/{memberId}/nickname-history")
    public List<MemberNicknameHistoryDto> readMemberNicknameHistory(@PathVariable Long memberId) {
        return memberReadService.readMemberNicknameHistory(memberId);
    }

    @PostMapping
    public MemberDto create(@RequestBody MemberCreateCommand memberCreateCommand) {
        Member member = memberCreateService.create(memberCreateCommand);
        return memberReadService.toDto(member);
    }

    @PostMapping("/{id}/nickname")
    public MemberDto updateNickname(@PathVariable Long id, @RequestBody String nickname) {
        memberCreateService.updateNickname(id, nickname);
        return memberReadService.read(id);
    }

}
