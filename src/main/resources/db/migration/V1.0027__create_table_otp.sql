CREATE TABLE OTP
(
    id serial primary key,
    otp_code int,
    email_Account varchar(100),
    mtime timestamp NULL,
    issue_At bigint,
    status int
);