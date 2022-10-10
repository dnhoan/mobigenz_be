CREATE TABLE specifications
(
    id serial primary key,
    specification_name varchar(100) unique not null,
    specification_group_id bigint,
    ctime timestamp NOT NULL DEFAULT current_timestamp,
    mtime timestamp NULL DEFAULT NULL,
    status int default 1,
    constraint fk_manufacturers FOREIGN KEY (specification_group_id) REFERENCES specification_groups(id)
);