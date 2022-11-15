CREATE TABLE customers_address
(
    id serial primary key,
    ward varchar(100),
    city varchar(100),
    district varchar(100),
    detail_address varchar(100),
    customer_id int,
    status int,
    constraint fk_customers FOREIGN KEY (customer_id) REFERENCES customers(id)
);