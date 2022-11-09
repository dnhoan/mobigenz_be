CREATE TABLE accounts
(
    id serial primary key,
    email varchar(100) unique not null,
    password varchar(100) not null,
    role_id int,
    ctime timestamp NOT NULL DEFAULT current_timestamp,
    mtime timestamp NULL,
    status int,
    constraint fk_roles FOREIGN KEY (role_id) REFERENCES roles(id)
);