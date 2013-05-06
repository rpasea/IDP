create database AuctionHouse_TeamDingDong;

use AuctionHouse_TeamDingDong;

create table users (
	id INT(10) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
	name VARCHAR(30) NOT NULL,
	password VARCHAR(30) NOT NULL,
	role INT(2) NOT NULL
);

create table online_users (
	user_id INT(10) NOT NULL,
	ip_addr VARCHAR(30) NOT NULL,
	port INT(10) NOT NULL,
	FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE CASCADE,
	primary key (user_id)
);

create table services (
	id INT(10) UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
	name VARCHAR(30) NOT NULL
);

create table buyers_to_services (
	user_id INT(10) NOT NULL,
	service_id INT(10) NOT NULL,
	FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (service_id) REFERENCES services(service_id) ON UPDATE CASCADE ON DELETE CASCADE,
	primary key (user_id, service_id)
);

create table sellers_to_services (
	user_id INT(10) NOT NULL,
	service_id INT(10) NOT NULL,
	FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (service_id) REFERENCES services(service_id) ON UPDATE CASCADE ON DELETE CASCADE,
	primary key (user_id, service_id)
);

-- Tabel in plus, simbolizeaza niste relatii permanente intre useri si servicii
-- (adica nu se schimba la login)
-- create table users_to_services (
-- 	user_id INT(10) NOT NULL,
-- 	service_id INT(10) NOT NULL,
-- 	FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE CASCADE,
-- 	FOREIGN KEY (service_id) REFERENCES services(service_id) ON UPDATE CASCADE ON DELETE CASCADE,
-- 	primary key (user_id, service_id)
-- );

INSERT INTO users (id, name, password, role)
 VALUES(null, 'gicu', 'p', 1);
INSERT INTO users (id, name, password, role)
 VALUES(null, 'Lulache', 'p', 0);
INSERT INTO users (id, name, password, role)
 VALUES(null, 'vene', 'p', 0);
-- INSERT INTO services (id, name)
--  VALUES(null, 'BaniGratis');
-- INSERT INTO users_to_services (user_id, service_id)
--  VALUES(1, 1);
-- INSERT INTO users_to_services (user_id, service_id)
--  VALUES(2, 1);
-- INSERT INTO users_to_services (user_id, service_id)
--  VALUES(3, 1);