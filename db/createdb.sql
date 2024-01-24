create table users
(
    user_id    int auto_increment
        primary key,
    username   varchar(50) not null,
    password   varchar(50) not null,
    first_name varchar(50) not null,
    last_name  varchar(50) not null,
    email      varchar(50) not null,
    is_blocked tinyint(1) not null
);

create table posts
(
    post_id        int auto_increment
        primary key,
    created_by     int           not null,
    title          varchar(64)   not null,
    content        varchar(8192) not null,
    total_likes    int default 0 not null,
    total_dislikes int default 0 not null,
    constraint posts_users_user_id_fk
        foreign key (created_by) references users (user_id)
);

create table comments
(
    comment_id int auto_increment
        primary key,
    content    varchar(8192) not null,
    user_id    int           not null,
    post_id    int           not null,
    constraint comments_posts_post_id_fk
        foreign key (post_id) references posts (post_id),
    constraint comments_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create table user_likes
(
    user_id     int not null,
    post_id     int not null,
    is_liked    tinyint(1) not null,
    is_disliked tinyint(1) not null,
    constraint user_likes_posts_post_id_fk
        foreign key (post_id) references posts (post_id),
    constraint user_likes_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

create table admins
(
    user_id      int not null,
    phone_number int null,
    constraint admins_users_user_id_fk
        foreign key (user_id) references users (user_id)
);

