CREATE TABLE imei
(
    id serial primary key,
    imei varchar(20) unique not null,
    product_detail_id bigint,
    status int,
    constraint fk_product_details FOREIGN KEY (product_detail_id) REFERENCES product_details(id)
);