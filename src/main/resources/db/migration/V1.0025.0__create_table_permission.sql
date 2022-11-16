CREATE TABLE permission
(
    id serial primary key,
    account_id int,
    role_id int,
    constraint fk_accounts FOREIGN KEY (account_id) REFERENCES accounts(id),
    constraint fk_roles FOREIGN KEY (role_id) REFERENCES roles(id)
);