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
    constraint fk_products FOREIGN KEY (product_id) REFERENCES products(id)
);