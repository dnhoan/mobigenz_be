CREATE TABLE products_variants_options
(
    id serial primary key,
    price float,
    stock int,
    image text,
    note text,
    status int,
    product_id bigint,
    product_variant_id bigint,
    constraint fk_products FOREIGN KEY (product_id) REFERENCES products(id),
    constraint fk_products_variants FOREIGN KEY (product_variant_id) REFERENCES products_variants(id)
);