package com.example.mysqlserver.domain.follow.application;

import com.example.mysqlserver.domain.follow.domain.Follow;
import com.example.mysqlserver.domain.follow.domain.FollowRepository;
import com.example.mysqlserver.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.PrivateKey;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class FollowCreateService {

    private final FollowRepository followRepository;

    public void create(MemberDto fromMember, MemberDto toMember) {
        Assert.isTrue(!fromMember.id().equals(toMember.id()), "From, To 회원이 동일합니다.");
        var follow = Follow.builder()
                .fromMemberId(fromMember.id())
                .toMemberId(toMember.id())
                .createdAt(LocalDateTime.now())
                .build();
        followRepository.save(follow);
    }

}
