package com.example.mysqlserver.domain.application;

import com.example.mysqlserver.domain.follow.application.FollowReadService;
import com.example.mysqlserver.domain.follow.domain.Follow;
import com.example.mysqlserver.domain.member.application.MemberReadService;
import com.example.mysqlserver.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReadFollowMemberUseCase {

    private final MemberReadService memberReadService;

    private final FollowReadService fowFollowReadService;

    public List<MemberDto> execute(Long memberId) {
        var follows = fowFollowReadService.getFollowing(memberId);
        var toMemberIds = follows.stream().map(Follow::getToMemberId).toList();
        return memberReadService.read(toMemberIds);
    }

}
