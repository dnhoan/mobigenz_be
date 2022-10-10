CREATE TABLE imei
(
    id serial primary key,
    imei varchar(20) unique not null,
    product_variant_option_id bigint,
    status int,
    constraint fk_products_variants_options FOREIGN KEY (product_variant_option_id) REFERENCES products_variants_options(id)
);