package com.example.mysqlserver.domain.follow.application;

import com.example.mysqlserver.domain.follow.domain.Follow;
import com.example.mysqlserver.domain.follow.domain.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FollowReadService {

    private final FollowRepository followRepository;

    public List<Follow> findAllByFromMemberId(Long fromMemberId) {
        return followRepository.findAllByFromMemberId(fromMemberId);
    }

}
