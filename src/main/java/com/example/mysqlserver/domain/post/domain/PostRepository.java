package com.example.mysqlserver.domain.post.domain;

import com.example.mysqlserver.domain.post.dto.DailyPostCountRequest;
import com.example.mysqlserver.domain.post.dto.DailyPostCountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class PostRepository {

    private static final String TABLE = "Post";

    private static final RowMapper<Post> ROW_MAPPER = ((rs, rowNum) -> Post.builder()
            .id(rs.getLong("id"))
            .memberId(rs.getLong("memberId"))
            .contents(rs.getString("contents"))
            .createdDate(rs.getObject("createdDate", LocalDate.class))
            .createdAt(rs.getObject("createdAt", LocalDateTime.class))
            .build());

    private static final RowMapper<DailyPostCountResponse> DAILY_POST_COUNT_MAPPER = (rs, rowNum) ->
            new DailyPostCountResponse(rs.getLong("memberId"), rs.getObject("createdDate", LocalDate.class), rs.getLong("count"));

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Post save(Post post) {
        if (post.getId() == null) {
            return insert(post);
        }
        throw new UnsupportedOperationException("갱신을 지원하지 않습니다.");
    }

    private Post insert(Post post) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");
        SqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(post);
        var id = simpleJdbcInsert.executeAndReturnKey(beanPropertySqlParameterSource).longValue();
        return Post.builder()
                .id(id)
                .memberId(post.getMemberId())
                .contents(post.getContents())
                .createdDate(post.getCreatedDate())
                .createdAt(post.getCreatedAt())
                .build();
    }

    public void bulkInsert(List<Post> posts) {
        var sql = String.format("""
                INSERT INTO %s (memberId, contents, createdDate, createdAt)
                VALUES (:memberId, :contents, :createdDate, :createdAt)
                """, TABLE);
        SqlParameterSource[] params = posts
                .stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);
        namedParameterJdbcTemplate.batchUpdate(sql, params);
    }

    public List<DailyPostCountResponse> groupByCratedDate(DailyPostCountRequest dailyPostCountRequest) {
        var sql = String.format("""
                SELECT createdDate, memberId, count(id) as count
                FROM %s
                WHERE memberId = :memberId and createdDate between :startDate and :endDate
                GROUP BY memberId, createdDate
                """, TABLE);
        var params = new BeanPropertySqlParameterSource(dailyPostCountRequest);
        return namedParameterJdbcTemplate.query(sql, params, DAILY_POST_COUNT_MAPPER);
    }

}
