CREATE TABLE options_values
(
    id serial primary key,
    option_value_name varchar(100) not null,
    option_name varchar(100) not null,
    note varchar,
    option_id bigint,
    ctime timestamp DEFAULT current_timestamp,
    mtime timestamp DEFAULT NULL,
    status int default 1,
    CONSTRAINT fk_options FOREIGN KEY(option_id) REFERENCES options(id)
);