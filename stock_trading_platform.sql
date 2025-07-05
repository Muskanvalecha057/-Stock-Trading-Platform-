create database stock_trading_market
use stock_trading_market

create table stock(id int primary key, name varchar(30) , 
price float,
quantity int)


CREATE TABLE users (
    id INT PRIMARY KEY,
    username VARCHAR(50),
    password VARCHAR(50),
    balance FLOAT
);

CREATE TABLE portfolio (
    id INT PRIMARY KEY IDENTITY,
    user_id INT FOREIGN KEY REFERENCES users(id),
    stock_id INT FOREIGN KEY REFERENCES stock(id),
    quantity INT,
    buy_price FLOAT
);


INSERT INTO stock (id, name, price, quantity) VALUES
(1, 'Apple', 340.50, 100),
(2, 'Tesla', 720.00, 80),
(3, 'TCS', 2150.75, 50),
(4, 'Amazon', 2850.20, 60),
(5, 'Google', 2600.00, 90);

INSERT INTO users (id, username, password, balance) VALUES
(1, 'muskan', '1234', 50000),
(2, 'john', 'abcd', 75000);

INSERT INTO portfolio (user_id, stock_id, quantity, buy_price) VALUES
(1, 1, 10, 330.00),  
(1, 3, 5, 2100.00);   

SELECT * FROM stock;
SELECT * FROM users;
SELECT * FROM portfolio;




