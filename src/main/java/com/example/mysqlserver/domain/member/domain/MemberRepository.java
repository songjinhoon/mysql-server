package com.example.mysqlserver.domain.member.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberRepository {

    private static final String TABLE = "Member";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final RowMapper<Member> ROW_MAPPER = ((rs, rowNum) -> Member.builder()
            .id(rs.getLong("id"))
            .email(rs.getString("email"))
            .nickname(rs.getString("nickname"))
            .birthday(rs.getObject("birthday", LocalDate.class))
            .createdAt(rs.getObject("createdAt", LocalDateTime.class))
            .build());

    public Optional<Member> findById(Long id) {
        var sql = String.format("SELECT * FROM %s WHERE id = :id", TABLE);
        var param = new MapSqlParameterSource().addValue("id", id);
        var member = namedParameterJdbcTemplate.queryForObject(sql, param, ROW_MAPPER);
        return Optional.ofNullable(member);
    }

    public List<Member> findAllByIdIn(List<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }
        var sql = String.format("SELECT * FROM %s WHERE id in (:ids)", TABLE);
        var params = new MapSqlParameterSource().addValue("ids", ids);
        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
    }

    public Member save(Member member) {
        if (member.getId() == null) {
            return insert(member);
        }
        return update(member);
    }

    private Member insert(Member member) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("Member")
                .usingGeneratedKeyColumns("id");
        SqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(member);
        var id = simpleJdbcInsert.executeAndReturnKey(beanPropertySqlParameterSource).longValue();
        return Member.builder()
                .id(id)
                .nickname(member.getNickname())
                .email(member.getEmail())
                .birthday(member.getBirthday())
                .createdAt(member.getCreatedAt())
                .build();
    }

    public Member update(Member member) {
        var sql = String.format("UPDATE %s set email = :email, nickname = :nickname, birthday = :birthday WHERE id = :id", TABLE);
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(member);
        namedParameterJdbcTemplate.update(sql, sqlParameterSource);
        return member;
    }

}
