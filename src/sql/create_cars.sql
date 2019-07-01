CREATE TABLE cars
(
    brand    varchar(80),
    type     varchar(80),
    price    double,
    owner_id int,
    FOREIGN KEY (owner_id) REFERENCES car_owners (id)
);
