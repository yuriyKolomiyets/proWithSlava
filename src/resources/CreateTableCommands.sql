CREATE TABLE users (
    id int NOT NULL PRIMARY KEY, address_id int NOT NULL,
    first_name VARCHAR(40), last_name VARCHAR(40), age int, CONSTRAINT FK_address
    FOREIGN KEY (address_id) REFERENCES address (id)
    );

CREATE TABLE address(
    id int NOT NULL PRIMARY KEY,
    city VARCHAR(20),
    street VARCHAR(40),
    house int
);

ALTER TABLE users MODIFY COLUMN id INT auto_increment;

INSERT INTO address (id, city, street, house) VALUES (1, 'Kyiv', 'street1', 1);
INSERT INTO address (id, city, street, house) VALUES (2, 'Kyiv', 'street1', 1);

INSERT INTO users (id, first_name, last_name, age, address_id) VALUES (1, 'Oleg', 'Kuznetsov', 20, 1);
INSERT INTO users (id, first_name, last_name, age, address_id) VALUES (2, 'Slava', 'Bizyanov', 30, 2);
INSERT INTO users (id, first_name, last_name, age, address_id) VALUES (3, 'Ivan', 'Ivanov', 30, 1);