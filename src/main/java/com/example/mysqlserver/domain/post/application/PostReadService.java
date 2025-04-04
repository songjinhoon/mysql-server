package com.example.mysqlserver.domain.post.application;

import com.example.mysqlserver.domain.post.domain.PostRepository;
import com.example.mysqlserver.domain.post.dto.DailyPostCountRequest;
import com.example.mysqlserver.domain.post.dto.DailyPostCountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostReadService {

    private final PostRepository postRepository;

    public List<DailyPostCountResponse> getDailyPostCount(DailyPostCountRequest dailyPostCountRequest) {
        return postRepository.groupByCratedDate(dailyPostCountRequest);
    }

}
