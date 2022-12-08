CREATE TABLE OPTIONS
(
    id SERIAL PRIMARY KEY,
    option_name varchar(100) unique not null ,
    note varchar,
    ctime timestamp DEFAULT current_timestamp,
    mtime timestamp DEFAULT NULL,
    status int default 1
);