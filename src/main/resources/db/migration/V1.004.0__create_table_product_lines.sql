CREATE TABLE product_lines
(
    id serial primary key,
    product_line_name varchar(100) not null,
    manufacturer_id bigint,
    ctime timestamp DEFAULT current_timestamp,
    mtime timestamp DEFAULT NULL,
    status int default 1,
    constraint fk_manufacturers FOREIGN KEY (manufacturer_id) REFERENCES manufacturers(id)
);