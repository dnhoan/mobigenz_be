CREATE TABLE customers
(
    id serial primary key,
    customer_name varchar(100) not null,
    phone_number varchar(20)  not null,
    email varchar(50) not null,
    birthday date not null,
    image text,
    gender int,
    customer_type int,
    account_id bigint,
    citizen_identify_cart varchar(12) null,
    ctime timestamp DEFAULT current_timestamp,
    mtime timestamp DEFAULT NULL,
    status int,
    constraint fk_accounts FOREIGN KEY (account_id) REFERENCES accounts(id)
);