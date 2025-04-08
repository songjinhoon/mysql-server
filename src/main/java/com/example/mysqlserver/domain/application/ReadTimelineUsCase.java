package com.example.mysqlserver.domain.application;

import com.example.mysqlserver.domain.follow.application.FollowReadService;
import com.example.mysqlserver.domain.follow.domain.Follow;
import com.example.mysqlserver.domain.post.application.PostReadService;
import com.example.mysqlserver.util.CursorRequest;
import com.example.mysqlserver.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReadTimelineUsCase {

    private final FollowReadService followReadService;

    private final PostReadService postReadService;

    public PageCursor<?> execute(Long memberId, CursorRequest cursorRequest) {
        var follows = followReadService.findAllByFromMemberId(memberId);
        var memberIds = follows.stream().map(Follow::getToMemberId).toList();
        return postReadService.getPosts(memberIds, cursorRequest);
    }

}
