CREATE TABLE products_variants
(
    id serial primary key,
    option_value_id bigint,
    product_option_id bigint,
    constraint fk_options_values FOREIGN KEY (option_value_id) REFERENCES options_values(id),
    constraint fk_products_options FOREIGN KEY (product_option_id) REFERENCES products_options(id)
);