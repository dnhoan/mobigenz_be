CREATE TABLE OPTIONS
(
    id SERIAL PRIMARY KEY,
    option_name varchar(100) unique not null ,
    note varchar
);