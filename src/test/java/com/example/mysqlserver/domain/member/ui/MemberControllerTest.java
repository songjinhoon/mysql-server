package com.example.mysqlserver.domain.member.ui;

import com.example.mysqlserver.utils.MemberFixtureFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class MemberControllerTest {


    @DisplayName("닉네임 변경")
    @Test
    public void updateNickname() {
        var member = MemberFixtureFactory.create();
        var expected = "nickname";

        member.updateNickname(expected);

        Assertions.assertEquals(expected, member.getNickname());
    }

    @DisplayName("닉네임 변경 실패")
    @Test
    public void updateNicknameError() {
        var member = MemberFixtureFactory.create();
        var expected = "nicknamenicknamenickname";

        Assertions.assertThrows(IllegalArgumentException.class, () -> member.updateNickname(expected));
    }

}