CREATE TABLE products_specification_groups
(
    id serial primary key,
    product_id bigint,
    specification_group_id bigint,
    constraint fk_specification_groups FOREIGN KEY (specification_group_id) REFERENCES specification_groups(id),
    constraint fk_products FOREIGN KEY (product_id) REFERENCES products(id)
);