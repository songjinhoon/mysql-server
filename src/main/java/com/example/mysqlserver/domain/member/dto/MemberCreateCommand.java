package com.example.mysqlserver.domain.member.dto;

import java.time.LocalDate;

public record MemberCreateCommand(
        String email,
        String nickname,
        LocalDate birthday
) {
}