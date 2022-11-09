CREATE TABLE transactions
(
    id serial primary key,
    total_money float,
    payment_method int,
    ctime timestamp NOT NULL DEFAULT current_timestamp,
    note text
);