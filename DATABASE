CREATE DATABASE IF NOT EXISTS bank_db;
USE bank_db;

CREATE TABLE IF NOT EXISTS accounts (
    account_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_no   VARCHAR(20) UNIQUE NOT NULL,
    holder_name  VARCHAR(100) NOT NULL,
    email        VARCHAR(100),
    phone        VARCHAR(15),
    balance      DECIMAL(15,2) DEFAULT 0.00,
    account_type ENUM('SAVINGS','CURRENT') DEFAULT 'SAVINGS',
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS transactions (
    txn_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_no  VARCHAR(20) NOT NULL,
    txn_type    ENUM('DEPOSIT','WITHDRAWAL','TRANSFER') NOT NULL,
    amount      DECIMAL(15,2) NOT NULL,
    description VARCHAR(255),
    txn_date    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_no) REFERENCES accounts(account_no)
);
