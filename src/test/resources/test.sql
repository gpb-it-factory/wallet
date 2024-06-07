CREATE SCHEMA IF NOT EXISTS gpb;

CREATE TABLE IF NOT EXISTS wallet (
    client_id VARCHAR NOT NULL,
    balance DECIMAL(19, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    last_updated TIMESTAMP NOT NULL);

INSERT INTO gpb.wallet (client_id, balance, currency, last_updated)
VALUES (
           '550e8400-e29b-41d4-a716-446655440000',
           100.00,
           'RUB',
           '2024-06-01 13:00:00');