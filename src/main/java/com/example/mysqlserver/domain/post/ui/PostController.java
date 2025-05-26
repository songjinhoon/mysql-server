package com.example.mysqlserver.domain.post.ui;

import com.example.mysqlserver.domain.application.CreatePostLikeUseCase;
import com.example.mysqlserver.domain.application.CreatePostUseCase;
import com.example.mysqlserver.domain.application.ReadTimelineUsCase;
import com.example.mysqlserver.domain.post.application.PostReadService;
import com.example.mysqlserver.domain.post.application.PostUpdateService;
import com.example.mysqlserver.domain.post.dto.DailyPostCountRequest;
import com.example.mysqlserver.domain.post.dto.PostCreateCommand;
import com.example.mysqlserver.util.CursorRequest;
import com.example.mysqlserver.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(value = "/posts")
@RestController
public class PostController {

    private final CreatePostUseCase createPostUseCase;

    private final PostReadService postReadService;

    private final PostUpdateService postUpdateService;

    private final CreatePostLikeUseCase createPostLikeUseCase;

    private final ReadTimelineUsCase readTimelineUsCase;

    @PostMapping
    public Long create(@RequestBody PostCreateCommand postCreateCommand) {
        return createPostUseCase.execute(postCreateCommand);
    }

    @GetMapping
    public Page<?> query(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, PostQueryDto postQueryDto) {
        return postReadService.query(pageable, postQueryDto);
    }

    @GetMapping("/members/{memberId}/by-cursor")
    public PageCursor<?> query(@PathVariable Long memberId, CursorRequest cursorRequest) {
        return postReadService.getPosts(memberId, cursorRequest);
    }

    @GetMapping("/daily-post-counts")
    public List<?> read(DailyPostCountRequest dailyPostCountRequest) {
        return postReadService.getDailyPostCount(dailyPostCountRequest);
    }

    @GetMapping("/members/{memberId}/timeline")
    public PageCursor<?> read(@PathVariable Long memberId, CursorRequest cursorRequest) {
//        return readTimelineUsCase.execute(memberId, cursorRequest);
        return readTimelineUsCase.executeTimeline(memberId, cursorRequest);
    }

    @PostMapping("/{id}/like")
    public void updatePost(@PathVariable Long id) {
//        postUpdateService.likePost(id);
        postUpdateService.likePostByOptimisticLock(id);
    }

    @PostMapping("/{id}/like/{memberId}")
    public void createPostLike(@PathVariable Long id, @PathVariable Long memberId) {
//        postUpdateService.likePost(id);
        createPostLikeUseCase.execute(id, memberId);
    }

}
