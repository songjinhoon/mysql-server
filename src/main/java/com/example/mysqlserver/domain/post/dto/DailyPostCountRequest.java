package com.example.mysqlserver.domain.post.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record DailyPostCountRequest(
        Long memberId,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate startDate,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate endDate
) {
}
