CREATE TABLE account(
    account_id BIGINT NOT NULL AUTO_INCREMENT,
    document_number VARCHAR(255),
    PRIMARY KEY(account_id)
) ENGINE = InnoDB;

CREATE TABLE operation_type(
    operation_type_id BIGINT NOT NULL AUTO_INCREMENT,
    description VARCHAR(255) NOT NULL,
    PRIMARY KEY(operation_type_id)
) ENGINE = InnoDB;

CREATE TABLE transaction(
    transaction_id BIGINT NOT NULL AUTO_INCREMENT,
    amount DECIMAL(19, 2) NOT NULL,
    event_date DATETIME(6) NOT NULL,
    transaction_code VARCHAR(255) NOT NULL,
    account_id BIGINT,
    operation_type_id BIGINT,
    PRIMARY KEY(transaction_id)
) ENGINE = InnoDB;

ALTER TABLE transaction ADD CONSTRAINT
    uk_transaction_transaction_CODE UNIQUE(transaction_code);

ALTER TABLE transaction ADD CONSTRAINT
    fk_transaction_account_ID FOREIGN KEY(account_id) REFERENCES account(account_id);

ALTER TABLE transaction ADD CONSTRAINT
    fk_transaction_operation_type_ID FOREIGN KEY(operation_type_id) REFERENCES operation_type(operation_type_id);