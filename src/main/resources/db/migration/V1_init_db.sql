create table Products
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(100)  NOT NULL,
    price       DECIMAL(7, 2) NOT NULL,
    description TEXT
);

create table Users
(
    id            SERIAL PRIMARY KEY,
    email         VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(200)       NOT NULL,
    salt          VARCHAR(200)       NOT NULL,
    user_role     VARCHAR(20)        NOT NULL
);


