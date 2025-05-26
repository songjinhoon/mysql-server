package com.example.mysqlserver.domain.application;

import com.example.mysqlserver.domain.follow.application.FollowReadService;
import com.example.mysqlserver.domain.follow.domain.Follow;
import com.example.mysqlserver.domain.post.application.PostCreateService;
import com.example.mysqlserver.domain.post.dto.PostCreateCommand;
import com.example.mysqlserver.domain.timeline.application.TimelineCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CreatePostUseCase {

    private final PostCreateService postCreateService;

    private final FollowReadService followReadService;

    private final TimelineCreateService timelineCreateService;

    //    이거 트랜잭션 걸면 말이안됨 -> Follower가 많다면 오래걸림
    public Long execute(PostCreateCommand postCreateCommand) {
        var postId = postCreateService.create(postCreateCommand);
        var fromMemberIds = followReadService.getFollower(postCreateCommand.memberId()).stream().map(Follow::getFromMemberId).toList();
        timelineCreateService.create(postId, fromMemberIds);
        return postId;
    }

}
