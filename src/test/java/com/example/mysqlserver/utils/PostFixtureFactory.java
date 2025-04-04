package com.example.mysqlserver.utils;

import com.example.mysqlserver.domain.post.domain.Post;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import java.time.LocalDate;

import static org.jeasy.random.FieldPredicates.*;

public class PostFixtureFactory {

    static public EasyRandom get(Long memberId, LocalDate startDate, LocalDate endDate) {
        var idPredicate = named("id")
                .and(ofType(Long.class))
                .and(inClass(Post.class))
                ;
        var memberIdPredicate = named("memberId")
                .and(ofType(Long.class))
                .and(inClass(Post.class))
                ;
        var param = new EasyRandomParameters()
                .excludeField(idPredicate)
                .dateRange(startDate, endDate)
                .randomize(memberIdPredicate, () -> memberId)
                ;
        return new EasyRandom(param);
    }

}
