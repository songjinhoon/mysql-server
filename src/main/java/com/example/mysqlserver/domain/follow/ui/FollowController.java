package com.example.mysqlserver.domain.follow.ui;

import com.example.mysqlserver.domain.application.CreateFollowMemberUseCase;
import com.example.mysqlserver.domain.application.ReadFollowMemberUseCase;
import com.example.mysqlserver.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/follows")
@RestController
public class FollowController {

    private final CreateFollowMemberUseCase createFollowMemberUseCase;

    private final ReadFollowMemberUseCase readFollowMemberUseCase;

    @PostMapping("/{fromId}/{toId}")
    public void create(@PathVariable Long fromId, @PathVariable Long toId) {
        createFollowMemberUseCase.execute(fromId, toId);
    }

    @GetMapping("/members/{fromId}")
    public List<?> read(@PathVariable Long fromId) {
        return readFollowMemberUseCase.execute(fromId);
    }

}
