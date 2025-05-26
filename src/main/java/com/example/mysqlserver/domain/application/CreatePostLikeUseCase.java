package com.example.mysqlserver.domain.application;

import com.example.mysqlserver.domain.member.application.MemberReadService;
import com.example.mysqlserver.domain.post.application.PostLikeCreateService;
import com.example.mysqlserver.domain.post.application.PostReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreatePostLikeUseCase {

    private final PostReadService postReadService;

    private final MemberReadService memberReadService;

    private final PostLikeCreateService postLikeCreateService;

    public void execute(Long postId, Long memberId) {
        var post = postReadService.read(postId);
        var member = memberReadService.read(memberId);
        postLikeCreateService.create(post, member);
    }

}
