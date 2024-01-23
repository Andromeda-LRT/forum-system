create table users
(
    user_id      int auto_increment primary key,
    username     varchar(50) not null,
    password     varchar(50) not null,
    first_name   varchar(50) not null,
    last_name    varchar(50) not null,
    email        varchar(50) not null,
    is_admin     boolean     not null,
    phone_number int
);

create table posts
(
    post_id    int auto_increment primary key,
    created_by int           not null,
    title      varchar(64)   not null,
    content    varchar(8192) not null,
    likes      int           not null DEFAULT 0,
    dislikes   int           not null DEFAULT 0,
    constraint posts_users_user_id_fk
        foreign key (created_by) references users (user_id)
);

create table comments
(
    comment_id int auto_increment primary key,
    content    varchar(8192) not null,
    user_id    int           not null,
    constraint comments_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create table post_comments
(
    post_id    int not null,
    comment_id int not null,
    constraint posts_post_comments_post_id_fk
        foreign key (post_id) references posts (post_id),
    constraint comments_post_comments_comment_id_fk
        foreign key (comment_id) references comments (comment_id)
);