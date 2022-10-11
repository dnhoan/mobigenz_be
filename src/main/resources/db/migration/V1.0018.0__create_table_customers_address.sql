CREATE TABLE customers_address
(
    id serial primary key,
    customer_id float,
    payment_method int,
    ctime timestamp NOT NULL DEFAULT current_timestamp,
    note text
);