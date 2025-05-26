package com.example.mysqlserver.domain.post.application;

import com.example.mysqlserver.domain.post.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostUpdateService {

    private final PostRepository postRepository;

    /* 비관적락 */
    @Transactional
    public void likePost(Long id) {
        var post = postRepository.findById(id, true).orElseThrow();
        post.incrementLikeCount();
        postRepository.save(post);
    }

    /* 낙관적락 */
    public void likePostByOptimisticLock(Long id) {
        var post = postRepository.findById(id, false).orElseThrow();
        post.incrementLikeCount();
        postRepository.save(post);
    }

}
