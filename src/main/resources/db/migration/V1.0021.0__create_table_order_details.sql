create table order_details
(
    id serial primary key,
    order_id int,
    product_detail_id int,
    price_sell float,
    product_price float,
    amount int,
    ctime timestamp NOT NULL DEFAULT current_timestamp ,
    mtime timestamp NULL DEFAULT NULL,
    note text,
    constraint fk_orders FOREIGN KEY (order_id) REFERENCES orders(id),
    constraint fk_product_details FOREIGN KEY (product_detail_id) REFERENCES product_details(id)
);

ALTER TABLE imei ADD order_detail_id int;
ALTER TABLE imei ADD CONSTRAINT fk_order_details FOREIGN KEY (order_detail_id) REFERENCES order_details(id);