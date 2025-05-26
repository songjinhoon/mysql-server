package com.example.mysqlserver.domain.post.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PostDto(
        Long id,

        Long memberId,

        String contents,

        Long likeCount,

        Long version,

        LocalDate createdDate,

        LocalDateTime createdAt) {
}
