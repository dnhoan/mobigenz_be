CREATE TABLE carts_items
(
    id serial primary key,
    amount int,
    cart_id bigint,
    product_detail_id bigint,
    constraint fk_carts FOREIGN KEY (cart_id) REFERENCES carts(id),
    constraint fk_product_details FOREIGN KEY (product_detail_id) REFERENCES product_details(id)
);
