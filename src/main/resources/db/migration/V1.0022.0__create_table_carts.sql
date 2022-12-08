CREATE TABLE carts
(
    id serial primary key,
    total_money float,
    items_amount int,
    customer_id bigint,
    constraint fk_customers FOREIGN KEY (customer_id) REFERENCES customers(id)
);