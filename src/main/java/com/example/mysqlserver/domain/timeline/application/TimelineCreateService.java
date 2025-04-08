package com.example.mysqlserver.domain.timeline.application;

import com.example.mysqlserver.domain.timeline.domain.Timeline;
import com.example.mysqlserver.domain.timeline.domain.TimelineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TimelineCreateService {

    private final TimelineRepository timelineRepository;

    public void create(Long postId, List<Long> toMemberIds) {
        var timelines = toMemberIds.stream()
                .map(memberId -> getBuild(postId, memberId))
                .toList();
        timelineRepository.bulkInsert(timelines);
    }

    /* TODO - refactoring  */
    private static Timeline getBuild(Long postId, Long memberId) {
        return Timeline.builder()
                .memberId(memberId)
                .postId(postId)
                .build();
    }

}
