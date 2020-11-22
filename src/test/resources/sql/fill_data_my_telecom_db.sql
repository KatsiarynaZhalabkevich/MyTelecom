USE `my_telecom`;
INSERT INTO `tariffs` (`name`, `description`, `speed`, `price`) VALUES 
('X5 MTS', 'unlim internet, WI-FI router', 100, 27.5),
('X6 MTS', 'unlim internet, WI-FI router, TV, dr. Web, TV tunner', 100, 38.5),
('X7 MTS','unlim internet, WI-FI router, TV, dr. Web, TV tunner', 200, 52.5),
('Combi solo A1','unlim internet, WI-FI router, dr. Web', 10, 11.9),
('Combi start A1', 'unlim internet, WI-FI router, dr. Web, TV tunner', 50, 24.9),
('Combi standart A1', 'unlim internet, WI-FI router, TV, dr. Web, TV tunner', 70, 34.90),
('Combi super A1', 'unlim internet, WI-FI router, TV, dr. Web, TV tunner', 100, 44.90);

INSERT INTO `promotions` (`description`, `date_start`, `date_end`, `discount`) VALUES
('Black November','2020-11-01', '2020-11-30', 0.5),
('Happy winter','2020-12-24', '2020-12-31', 0.3),
('Happy Birthday My Telecom', '2020-10-05', '2020-10-05', 1);

INSERT INTO `accounts` (`balance`, `registration_date`, `user_id`) VALUES
(5, '2020-11-18', 2),
(5, '2020-11-13', 5),
(10,'2020-11-15', 3),
(15, '2020-11-15', 13);

INSERT INTO `tariff_notes` (`connection_date`, `tariff_id`, `account_id`) VALUES
('2020-11-18', 1, 1),
('2020-11-15', 3, 2),
('2020-11-15',5, 4);