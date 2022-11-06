CREATE TABLE customers_address
(
    id serial primary key,
    customer_id int,
    payment_method int,
    ctime timestamp NOT NULL DEFAULT current_timestamp,
    note text,
    constraint fk_customers FOREIGN KEY (customer_id) REFERENCES customers(id)
);