package com.example.mysqlserver.domain.post;

import com.example.mysqlserver.domain.post.domain.Post;
import com.example.mysqlserver.domain.post.domain.PostRepository;
import com.example.mysqlserver.utils.PostFixtureFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.util.stream.IntStream;

@SpringBootTest
public class PostBulkInsertTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void bulkInsert() {
        var easyRandom = PostFixtureFactory.get(
                3L,
                LocalDate.of(2024, 4, 1),
                LocalDate.of(2024, 4, 30));

        var stopWatch = new StopWatch();
        var queryStopWatch = new StopWatch();

        stopWatch.start();
        var posts = IntStream.range(0, 10000 * 100)
                .parallel()
                .mapToObj(i -> easyRandom.nextObject(Post.class))
                .toList();
        stopWatch.stop();
        System.out.println("객체 생성 시간 :: " + stopWatch.getTotalTimeSeconds());

        queryStopWatch.start();
        postRepository.bulkInsert(posts);
        queryStopWatch.stop();
        System.out.println("쿼리 수행 시간 :: " + queryStopWatch.getTotalTimeSeconds());
    }

}
