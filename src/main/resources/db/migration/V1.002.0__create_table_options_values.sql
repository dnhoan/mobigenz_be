CREATE TABLE options_values
(
    id serial primary key,
    option_value_name varchar(100) unique not null,
    option_name varchar(100) not null,
    note varchar,
    option_id int,
    CONSTRAINT fk_options FOREIGN KEY(option_id) REFERENCES options(id)
);