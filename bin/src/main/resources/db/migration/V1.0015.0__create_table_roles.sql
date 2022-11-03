CREATE TABLE roles
(
    id serial primary key,
    role_name varchar(50) unique not null,
    ctime timestamp NOT NULL DEFAULT current_timestamp,
    mtime timestamp NULL,
    note text
);