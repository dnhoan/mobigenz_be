CREATE TABLE products
(
    id serial primary key,
    product_name varchar(100) unique not null,
    description text default  null,
    product_line_id bigint,
    ctime timestamp DEFAULT current_timestamp,
    mtime timestamp DEFAULT NULL,
    status int default 1,
    detail text,
    min_price float,
    max_price float,
    images text,
    constraint fk_manufacturers FOREIGN KEY (product_line_id) REFERENCES product_lines(id)
);