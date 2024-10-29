CREATE TABLE transaction_status
(
    code        TEXT PRIMARY KEY,
    description TEXT
);

INSERT INTO transaction_status (code, description)
    VALUES ('SUCCESS', 'Transaction is successful');
INSERT INTO transaction_status (code, description)
    VALUES ('FAILED', 'Transaction is failed');
INSERT INTO transaction_status (code, description)
    VALUES ('PENDING', 'Transaction is pending');

CREATE TABLE transaction
(
    tx_head      TEXT PRIMARY KEY,
    from_address TEXT,
    to_address   TEXT,
    amount       NUMERIC(38, 10),
    token_id     TEXT,
    status       TEXT,
    CONSTRAINT fk_status FOREIGN KEY (status) REFERENCES transaction_status (code)
);
