CREATE TABLE products_options
(
    id serial primary key,
    product_id bigint,
    option_id bigint,
    constraint fk_products FOREIGN KEY (product_id) REFERENCES products(id),
    constraint fk_options FOREIGN KEY (option_id) REFERENCES options(id)
);