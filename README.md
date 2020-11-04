create table usersLogin
(
    id       serial primary key,
    email    text not null,
    password text not null
);

create table usersProfiles
(
    id          serial primary key,
    user_id     int  not null,
    name        text not null,
    surname     text not null,
    description text,
    photo       bytea,
    chats       int[],
    foreign key (user_id) references usersLogin (id) on delete set null
);

create table chats
(
    id       serial primary key,
    name     text  not null,
    users_id int[] not null
);