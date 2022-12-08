create table logs_order_status
(
    id serial primary key,
    new_status int,
    time timestamp DEFAULT current_timestamp,
    user_change varchar(50),
    note text,
    order_id int,
    user_id int,
    constraint fk_orders FOREIGN KEY (order_id) REFERENCES orders(id)
);