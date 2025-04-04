package com.example.mysqlserver.domain.post.ui;

import com.example.mysqlserver.domain.post.application.PostCreateService;
import com.example.mysqlserver.domain.post.application.PostReadService;
import com.example.mysqlserver.domain.post.dto.DailyPostCountRequest;
import com.example.mysqlserver.domain.post.dto.PostCreateCommand;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/daily-post-counts")
    public List<?> read(DailyPostCountRequest dailyPostCountRequest) {
        return postReadService.getDailyPostCount(dailyPostCountRequest);
    }

}
