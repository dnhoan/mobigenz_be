CREATE TABLE manufacturers
(
    id serial primary key,
    manufacturer_name varchar(100) unique not null,
    ctime timestamp DEFAULT current_timestamp,
    mtime timestamp DEFAULT NULL,
    status int default 1
);