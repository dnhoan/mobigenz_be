CREATE TABLE accounts
(
    id serial primary key,
    email varchar(100) unique not null,
    password varchar(100) not null,
    ctime timestamp NOT NULL DEFAULT current_timestamp,
    mtime timestamp NULL,
    status int
);