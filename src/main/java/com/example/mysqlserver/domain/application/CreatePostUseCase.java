package com.example.mysqlserver.domain.application;

import com.example.mysqlserver.domain.follow.application.FollowReadService;
import com.example.mysqlserver.domain.follow.domain.Follow;
import com.example.mysqlserver.domain.post.application.PostCreateService;
import com.example.mysqlserver.domain.post.dto.PostCreateCommand;
import com.example.mysqlserver.domain.timeline.application.TimelineCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreatePostUseCase {

    private final PostCreateService postCreateService;

    private final FollowReadService followReadService;

    private final TimelineCreateService timelineCreateService;

    public Long execute(PostCreateCommand postCreateCommand) {
        var postId = postCreateService.create(postCreateCommand);
        var toMemberIds = followReadService.findAllByFromMemberId(postCreateCommand.memberId()).stream().map(Follow::getToMemberId).toList();
        timelineCreateService.create(postId, toMemberIds);
        return postId;
    }

}
