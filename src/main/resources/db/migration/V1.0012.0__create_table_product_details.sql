CREATE TABLE product_details
(
    id serial primary key,
    price float,
    sku varchar(100),
    stock int,
    image text,
    note text,
    status int,
    product_id bigint,
    product_name varchar(100),
    price_origin float,
    price_sell float,
    constraint fk_products FOREIGN KEY (product_id) REFERENCES products(id)
);