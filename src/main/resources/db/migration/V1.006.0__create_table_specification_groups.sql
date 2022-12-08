CREATE TABLE specification_groups
(
    id serial primary key,
    specification_group_name varchar(100) unique not null,
    ctime timestamp DEFAULT current_timestamp,
    mtime timestamp DEFAULT NULL,
    status int default 1
);