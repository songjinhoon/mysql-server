package com.example.mysqlserver.domain.member.dto;

import java.time.LocalDateTime;

public record MemberNicknameHistoryDto(
        Long memberId,
        String nickname,
        LocalDateTime createdAt) {
}
