DELIMITER //
CREATE PROCEDURE create_car(IN _brand varchar(800), IN _type varchar(800), IN _price double, IN _owner_id int)
BEGIN
    INSERT INTO cars (brand, type, price, owner_id) VALUES (_brand, _type, _price, _owner_id);
END //
DELIMITER ;