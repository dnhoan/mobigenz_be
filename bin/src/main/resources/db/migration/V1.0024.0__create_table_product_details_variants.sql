CREATE TABLE product_details_variants
(
    product_variant_id bigint,
    product_detail_id bigint,
    constraint fk_product_details FOREIGN KEY (product_detail_id) REFERENCES product_details(id),
    constraint fk_product_variants FOREIGN KEY (product_variant_id) REFERENCES products_variants(id),
    constraint pk_product_details_variants PRIMARY KEY (product_detail_id,product_variant_id)
);