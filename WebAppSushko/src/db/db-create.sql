DROP DATABASE IF EXISTS FinalProject;

CREATE DATABASE FinalProject;

USE FinalProject;

create TABLE users(id INT PRIMARY KEY AUTO_INCREMENT, login VARCHAR(25), pass VARCHAR(25), fio VARCHAR(100), balance INTEGER, tell VARCHAR(100), adress VARCHAR(100), administrator BOOLEAN, block BOOLEAN, UNIQUE (login));

INSERT INTO users VALUE(DEFAULT, 'admin', 'JE5aBpP7nto=', 'Sushko Alexey Evgenievich', 0, '+380951705211', 'Kharkov Lenin str. 25', TRUE, FALSE);
INSERT INTO users VALUE(DEFAULT, 'user1', 'X8DmJCUmO5A=', 'Petrov Petr Petrivich', 0, '+380951234567', 'Kyiv Shevchenko str. 14', FALSE, FALSE);
INSERT INTO users VALUE(DEFAULT, 'user2', 'mjJQC/ribH8=','Ivanov Ivan Ivanovich', 500, '+380957654321', 'Lviv Miry str. 12', FALSE, FALSE);
INSERT INTO users VALUE(DEFAULT, 'user3', 'pDdq636Jelc=','Shevchenco Ivan Andreevich', 0, '+380952222222', 'Kharkov Sumska str. 142', FALSE, FALSE);
INSERT INTO users VALUE(DEFAULT, 'user4', 'EzODCA+Sihg=','Kovalenko Stanislav Valeriovich', 0, '+380951111111', 'Lviv Tankopia str. 168', TRUE, FALSE);


create TABLE tariff(id INT PRIMARY KEY AUTO_INCREMENT, name_tariff VARCHAR(100), price INTEGER, comment VARCHAR(500), UNIQUE (name_tariff));

INSERT INTO tariff VALUE(DEFAULT, 'Internet', 0, 'This tariff will allow you to use the Internet at different speeds');
INSERT INTO tariff VALUE(DEFAULT, 'Television', 0, 'This tariff will allow you to enjoy watching movies and favorite TV shows');
INSERT INTO tariff VALUE(DEFAULT, 'Radio', 20, 'This tariff will allow you to listen to news wherever you are');


create TABLE service(id INT PRIMARY KEY AUTO_INCREMENT, name_service VARCHAR(100), price INTEGER, comment VARCHAR(500), UNIQUE (name_service));
INSERT INTO service VALUE(DEFAULT, 'Nornal speed', 50, 'This is the usual speed of the Internet, 50Mb/sec');
INSERT INTO service VALUE(DEFAULT, 'Fast internet', 100, 'It\'s high speed internet, 100Mb/sec');
INSERT INTO service VALUE(DEFAULT, 'Unlimited', 150, 'Internet without restrictions on the fastest speed, >100Mb/sec');
INSERT INTO service VALUE(DEFAULT, 'Analogue television', 50, 'this is the standard stream of broadcasting');
INSERT INTO service VALUE(DEFAULT, 'Satellite TV', 100, 'Great variety of channels on your TV');
INSERT INTO service VALUE(DEFAULT, 'Full HD', 50, 'super high quality video');
INSERT INTO service VALUE(DEFAULT, 'Standart Radio station', 20, '20 radio stations');
INSERT INTO service VALUE(DEFAULT, '100 percent', 50,'100 radio stations');



create TABLE users_tariff(user_id INT, tariff_id INT, service_id INT, FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE, FOREIGN KEY(tariff_id) REFERENCES tariff(id) ON DELETE CASCADE, FOREIGN KEY(service_id) REFERENCES service(id) ON DELETE CASCADE, UNIQUE(user_id, tariff_id, service_id));

INSERT INTO users_tariff VALUE(1, 1, 1);
INSERT INTO users_tariff VALUE(1, 1, 2);
INSERT INTO users_tariff VALUE(1, 1, 3);
INSERT INTO users_tariff VALUE(2, 2, 4);
INSERT INTO users_tariff VALUE(3, 2, 5);
INSERT INTO users_tariff VALUE(3, 2, 6);
INSERT INTO users_tariff VALUE(3, 3, 7);
INSERT INTO users_tariff VALUE(2, 3, 8);


create TABLE tariff_service(tariff_id INT, service_id INT , FOREIGN KEY(tariff_id) REFERENCES tariff(id) ON DELETE CASCADE, FOREIGN KEY(service_id) REFERENCES service(id) ON DELETE CASCADE, UNIQUE(service_id, tariff_id));
INSERT INTO tariff_service VALUE(1, 1);
INSERT INTO tariff_service VALUE(1, 2);
INSERT INTO tariff_service VALUE(1, 3);
INSERT INTO tariff_service VALUE(2, 4);
INSERT INTO tariff_service VALUE(2, 5);
INSERT INTO tariff_service VALUE(2, 6);
INSERT INTO tariff_service VALUE(3, 7);
INSERT INTO tariff_service VALUE(3, 8);



select * from users;
select * from tariff;
select * from service;
select * from users_tariff;
select * from tariff_service;

