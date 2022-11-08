CREATE TABLE product_variant_combinations
(
    id serial primary key,
    sku varchar(100) ,
    product_detail_id int,
    product_variant_id int,
    constraint fk_products_variants FOREIGN KEY (product_variant_id) REFERENCES products_variants(id),
    constraint fk_product_detail FOREIGN KEY (product_detail_id) REFERENCES product_details(id)
);