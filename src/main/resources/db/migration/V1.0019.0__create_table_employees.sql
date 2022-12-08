CREATE TABLE employees
(
    id serial primary key,
    employee_name varchar(100) not null,
    employee_code varchar(100) not null,
    account_id bigint,
    phone_number varchar(20) unique not null,
    address varchar(100) not null,
    birthday date not null,
    gender int,
    image text,
    email varchar(50) not null,
    cmnd_cccd varchar(20) unique null,
    salary float,
    time_onboard timestamp NULL ,
    day_off timestamp NULL,
    note text,
    status int default 1,
    ctime timestamp DEFAULT current_timestamp,
    mtime timestamp DEFAULT NULL,
    constraint fk_accounts FOREIGN KEY (account_id) REFERENCES accounts(id)
);