CREATE TABLE roles
(
    id serial primary key,
    role_name varchar(50) unique not null,
    ctime timestamp DEFAULT current_timestamp,
    mtime timestamp ,
    note text
);