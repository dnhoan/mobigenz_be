CREATE TABLE products_specifications
(
    id serial primary key,
    product_specification_group_id bigint,
    specification_id bigint,
    product_specification_name varchar(100) null,
    constraint fk_specifications FOREIGN KEY (specification_id) REFERENCES specifications(id),
    constraint fk_products_specification_groups FOREIGN KEY (product_specification_group_id) REFERENCES products_specification_groups(id)
);