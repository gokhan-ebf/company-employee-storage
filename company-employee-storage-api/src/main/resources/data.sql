DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS companies;

CREATE TABLE companies (
 id INTEGER  AUTO_INCREMENT PRIMARY KEY,
 name varchar(250) NOT NULL
);



CREATE TABLE employees (
 id INTEGER  AUTO_INCREMENT PRIMARY KEY,
 name VARCHAR (250) NOT NULL,
 surname VARCHAR(250) NOT NULL,
 email VARCHAR(250) DEFAULT NULL,
 address VARCHAR(250) DEFAULT NULL,
 salary DOUBLE DEFAULT 0,
 company_id INT,
 FOREIGN KEY(company_id) REFERENCES companies(id)
);

INSERT INTO companies (name) VALUES ('EBF'), ('Microsoft');

----Generated from https://www.mockaroo.com/-------
INSERT INTO employees (name, surname, email, address, salary, company_id) VALUES ('Noak', 'MacKain', 'nmackain0@stumbleupon.com', '284 Holy Cross Trail', 70144.82, 2);
INSERT INTO employees (name, surname, email, address, salary, company_id) VALUES ('Emmey', 'Bagnal', 'ebagnal1@wired.com', '49991 Clove Crossing', 69936.66, 1);
INSERT INTO employees (name, surname, email, address, salary, company_id) VALUES ('Crystal', 'Axell', 'caxell2@barnesandnoble.com', '59 Eliot Plaza', 51537.21, 2);
INSERT INTO employees (name, surname, email, address, salary, company_id) VALUES ('Geri', 'Vasentsov', 'gvasentsov3@blogs.com', '1123 Surrey Place', 46773.39, 1);
INSERT INTO employees (name, surname, email, address, salary, company_id) VALUES ('Shannon', 'Burrage', 'sburrage4@hp.com', '30103 Dottie Lane', 62677.45, 1);
INSERT INTO employees (name, surname, email, address, salary, company_id) VALUES ('Eugine', 'Noyes', 'enoyes5@walmart.com', '4891 Springs Way', 78700.32, 1);
INSERT INTO employees (name, surname, email, address, salary, company_id) VALUES ('Star', 'Janisson', 'sjanisson6@wix.com', '301 Fair Oaks Drive', 67620.09, 2);
INSERT INTO employees (name, surname, email, address, salary, company_id) VALUES ('Nahum', 'Domenici', 'ndomenici7@angelfire.com', '719 Macpherson Street', 59346.04, 1);
INSERT INTO employees (name, surname, email, address, salary, company_id) VALUES ('Con', 'Rizzetti', 'crizzetti8@reverbnation.com', '571 Banding Parkway', 51950.81, 1);
INSERT INTO employees (name, surname, email, address, salary, company_id) VALUES ('Gordan', 'Bruckmann', 'gbruckmann9@twitpic.com', '65103 Mallard Point', 57069.47, 2);
INSERT INTO employees (name, surname, email, address, salary, company_id) VALUES ('Huntington', 'Loxley', 'hloxleya@clickbank.net', '942 Loftsgordon Parkway', 64121.96, 1);
INSERT INTO employees (name, surname, email, address, salary, company_id) VALUES ('Berke', 'Aughtie', 'baughtieb@zdnet.com', '0 Bonner Crossing', 72313.84, 2);
INSERT INTO employees (name, surname, email, address, salary, company_id) VALUES ('Cassandra', 'Shankle', 'cshanklec@sphinn.com', '2 Claremont Avenue', 76486.56, 1);
INSERT INTO employees (name, surname, email, address, salary, company_id) VALUES ('Davida', 'Spyby', 'dspybyd@cisco.com', '83500 Melby Terrace', 50074.22, 1);
INSERT INTO employees (name, surname, email, address, salary, company_id) VALUES ('Pebrook', 'Winnard', 'pwinnarde@plala.or.jp', '1 Prairieview Parkway', 69392.82, 2);
