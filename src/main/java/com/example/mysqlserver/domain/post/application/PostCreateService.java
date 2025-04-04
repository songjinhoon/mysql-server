package com.example.mysqlserver.domain.post.application;

import com.example.mysqlserver.domain.post.domain.Post;
import com.example.mysqlserver.domain.post.domain.PostRepository;
import com.example.mysqlserver.domain.post.dto.PostCreateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostCreateService {

    private final PostRepository postRepository;

    public Long create(PostCreateCommand postCreateCommand) {
        var post = Post.builder()
                .memberId(postCreateCommand.memberId())
                .contents(postCreateCommand.contents())
                .build();
        return postRepository.save(post).getId();
    }

}
