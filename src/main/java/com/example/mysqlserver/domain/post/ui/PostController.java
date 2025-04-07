package com.example.mysqlserver.domain.post.ui;

import com.example.mysqlserver.domain.post.application.PostCreateService;
import com.example.mysqlserver.domain.post.application.PostReadService;
import com.example.mysqlserver.domain.post.dto.DailyPostCountRequest;
import com.example.mysqlserver.domain.post.dto.PostCreateCommand;
import com.example.mysqlserver.util.CursorRequest;
import com.example.mysqlserver.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(value = "/posts")
@RestController
public class PostController {

    private final PostCreateService postCreateService;

    private final PostReadService postReadService;

    @PostMapping
    public Long create(@RequestBody PostCreateCommand postCreateCommand) {
        return postCreateService.create(postCreateCommand);
    }

    @GetMapping
    public Page<?> query(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, PostQueryDto postQueryDto) {
        return postReadService.findAllByMemberId(pageable, postQueryDto);
    }

    @GetMapping("/members/{memberId}/by-cursor")
    public PageCursor<?> query(@PathVariable Long memberId, CursorRequest cursorRequest) {
        return postReadService.getPosts(memberId, cursorRequest);
    }

    @GetMapping("/daily-post-counts")
    public List<?> read(DailyPostCountRequest dailyPostCountRequest) {
        return postReadService.getDailyPostCount(dailyPostCountRequest);
    }

}
