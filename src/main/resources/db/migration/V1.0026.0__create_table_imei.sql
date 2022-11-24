CREATE TABLE imei
(
    id serial primary key,
    imei varchar(20) unique not null,
    product_detail_id bigint,
    status int,
    order_detail_id int,
    constraint fk_product_details FOREIGN KEY (product_detail_id) REFERENCES product_details(id),
    CONSTRAINT fk_order_details FOREIGN KEY (order_detail_id) REFERENCES order_details(id) ON DELETE SET NULL
);