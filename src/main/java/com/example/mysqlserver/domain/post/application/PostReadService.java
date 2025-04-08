package com.example.mysqlserver.domain.post.application;

import com.example.mysqlserver.domain.post.domain.Post;
import com.example.mysqlserver.domain.post.domain.PostRepository;
import com.example.mysqlserver.domain.post.dto.DailyPostCountRequest;
import com.example.mysqlserver.domain.post.dto.DailyPostCountResponse;
import com.example.mysqlserver.domain.post.ui.PostQueryDto;
import com.example.mysqlserver.util.CursorRequest;
import com.example.mysqlserver.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostReadService {

    private final PostRepository postRepository;

    public List<Post> findAllByIdIn(List<Long> ids) {
        return postRepository.findAllByIdIn(ids);
    }

    public List<DailyPostCountResponse> getDailyPostCount(DailyPostCountRequest dailyPostCountRequest) {
        return postRepository.groupByCratedDate(dailyPostCountRequest);
    }

    public Page<?> findAllByMemberId(Pageable pageRequest, PostQueryDto postQueryDto) {
        return postRepository.findAllByMemberId(pageRequest, postQueryDto);
    }

    public PageCursor<?> getPosts(Long memberId, CursorRequest cursorRequest) {
        var posts = findAllBy(memberId, cursorRequest);
        var nextKey = getNextKey(posts);
        return new PageCursor<>(cursorRequest.next(nextKey), posts);
    }

    public PageCursor<?> getPosts(List<Long> memberIds, CursorRequest cursorRequest) {
        var posts = findAllBy(memberIds, cursorRequest);
        var nextKey = getNextKey(posts);
        return new PageCursor<>(cursorRequest.next(nextKey), posts);
    }

    public List<Post> findAllBy(Long memberId, CursorRequest cursorRequest) {
        if (cursorRequest.hasKey()) {
            return postRepository.findAllByLessThenIdAndMemberIdAndOrderByIdDesc(cursorRequest.key(), memberId, cursorRequest.size());
        }
        return postRepository.findAllByMemberIdAndOrderByIdDesc(memberId, cursorRequest.size());
    }

    public List<Post> findAllBy(List<Long> memberIds, CursorRequest cursorRequest) {
        if (cursorRequest.hasKey()) {
            return postRepository.findAllByLessThenIdAndInMemberIdAndOrderByIdDesc(cursorRequest.key(), memberIds, cursorRequest.size());
        }
        return postRepository.findAllByInMemberIdAndOrderByIdDesc(memberIds, cursorRequest.size());
    }

    private static long getNextKey(List<Post> posts) {
        return posts.stream()
                .mapToLong(Post::getId)
                .min()
                .orElse(CursorRequest.NONE_KEY);
    }

}
