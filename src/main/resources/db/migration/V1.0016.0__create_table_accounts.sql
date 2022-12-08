CREATE TABLE accounts
(
    id serial primary key,
    email varchar(100) unique not null,
    phoneNumber varchar(15),
    password varchar(100) not null,
    ctime timestamp DEFAULT current_timestamp,
    mtime timestamp DEFAULT NULL,
    status int
);