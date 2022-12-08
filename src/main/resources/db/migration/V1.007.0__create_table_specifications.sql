CREATE TABLE specifications
(
    id serial primary key,
    specification_name varchar(100) not null,
    specification_group_id bigint,
    ctime timestamp DEFAULT current_timestamp,
    mtime timestamp DEFAULT NULL,
    status int default 1,
    constraint fk_specification_groups FOREIGN KEY (specification_group_id) REFERENCES specification_groups(id)
);