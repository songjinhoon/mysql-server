package com.example.mysqlserver.domain.application;

import com.example.mysqlserver.domain.follow.application.FollowCreateService;
import com.example.mysqlserver.domain.member.application.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateFollowMemberUseCase {

    private final MemberReadService memberReadService;

    private final FollowCreateService followCreateService;

    public void execute(Long fromMemberId, Long toMemberId) {
        var fromMember = memberReadService.read(fromMemberId);
        var toMember = memberReadService.read(toMemberId);
        followCreateService.create(fromMember, toMember);
    }

}
