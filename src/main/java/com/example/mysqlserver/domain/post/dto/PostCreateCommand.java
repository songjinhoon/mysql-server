package com.example.mysqlserver.domain.post.dto;

public record PostCreateCommand(Long memberId, String contents) {
}
