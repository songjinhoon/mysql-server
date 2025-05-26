create table Member
(
    id        int auto_increment,
    email     varchar(20) not null,
    nickname  varchar(20) not null,
    birthday  date        not null,
    createdAt datetime    not null,
    constraint member_id_uindex
        primary key (id)
);

create table MemberNicknameHistory
(
    id        int auto_increment,
    memberId  int         not null,
    nickname  varchar(20) not null,
    createdAt datetime    not null,
    constraint memberNicknameHistory_id_uindex
        primary key (id)
);

create table Follow
(
    id           int auto_increment,
    fromMemberId int      not null,
    toMemberId   int      not null,
    createdAt    datetime not null,
    constraint Follow_id_uindex
        primary key (id)
);

create unique index Follow_fromMemberId_toMemberId_uindex
    on Follow (fromMemberId, toMemberId);

create table Post
(
    id          int auto_increment,
    memberId    int          not null,
    contents    varchar(100) not null,
    likeContent int          not null,
    version     int          not null,
    createdDate date         not null,
    createdAt   datetime     not null,
    constraint POST_id_uindex
        primary key (id)
);

create index POST__index_member_id
    on Post (memberId);

create index POST__index_created_date
    on Post (createdDate);

create index POST__index_member_id_created_date
    on Post (memberId, createdDate);

create table Timeline
(
    id        int auto_increment,
    memberId  int      not null,
    postId    int      not null,
    createdAt datetime not null,
    constraint timeline_id_uindex
        primary key (id)
);

-- Timeline 과 스키마가 돌히자만, 테이블의 성질이 다르기에 하나의 테이블로 사용하면 안됨 (하나는 뷰의 용도고 하나는 집계의 용도임)
create table PostLike
(
    id        int auto_increment,
    memberId  int      not null,
    postId    int      not null,
    createdAt datetime not null,
    constraint timeline_id_uindex
        primary key (id)
);