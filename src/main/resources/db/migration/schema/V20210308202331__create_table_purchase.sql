CREATE TABLE tb_purchase (
        id int8 NOT NULL,
        specific_id_client VARCHAR(500) NOT NULL,
        specific_id_books
        purchased_books int8 NOT NULL,
        amount_to_pay float8 NOT NULL,
        purchase_status VARCHAR(10) NOT NULL,
        CONSTRAINT purchase_id PRIMARY KEY (id)
);