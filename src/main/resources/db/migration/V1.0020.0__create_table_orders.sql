create table orders
(
    id serial primary key,
    customer_id int,
    transaction_id int,
    recipient_name varchar(100),
    recipient_phone varchar(100),
    recipient_email varchar(100),
    address varchar(200),
    total_money float,
    ship_fee float,
    goods_value float,
    checkout float,
    quantity int,
    ship_date timestamp,
    carrier varchar(100),
    ctime timestamp NOT NULL DEFAULT current_timestamp ,
    mtime timestamp NULL DEFAULT NULL,
    pay_status int,
    order_status int,
    note text,
    constraint fk_transactions FOREIGN KEY (transaction_id) REFERENCES transactions(id),
    constraint fk_customers FOREIGN KEY (customer_id) REFERENCES customers(id)
);
