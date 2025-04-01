package com.example.mysqlserver.domain.member.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Member {

    private final Long id;

    private String nickname;

    private final String email;

    private final LocalDate birthday;

    private final LocalDateTime createdAt;

    private final static Long MAX_LENGTH_NAME = 10L;

    @Builder
    public Member(Long id, String nickname, String email, LocalDate birthday, LocalDateTime createdAt) {
        this.id = id;
        validateNickname(nickname);
        this.nickname = Objects.requireNonNull(nickname);
        this.email = Objects.requireNonNull(email);
        this.birthday = Objects.requireNonNull(birthday);
        this.createdAt = createdAt;
    }

    public void updateNickname(String other) {
        Objects.requireNonNull(other);
        validateNickname(other);
        this.nickname = other;
    }

    private void validateNickname(String nickname) {
        Assert.isTrue(nickname.length() <= MAX_LENGTH_NAME, "최대 길이를 초과했습니다.");
    }

}
