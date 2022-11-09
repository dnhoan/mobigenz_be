CREATE TABLE manufacturers
(
    id serial primary key,
    manufacturer_name varchar(100) unique not null,
    ctime timestamp NOT NULL DEFAULT current_timestamp,
    mtime timestamp NULL DEFAULT NULL,
    status int default 1
);