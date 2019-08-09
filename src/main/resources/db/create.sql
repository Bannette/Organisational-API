set mode postgresSQL;

CREATE TABLE IF NOT EXISTS departments (
 id int PRIMARY KEY auto_increment,
departmentname VARCHAR,
description VARCHAR,
numberofemployees VARCHAR,
);
CREATE TABLE IF NOT EXISTS news (
 id int PRIMARY KEY auto_increment,
content VARCHAR,
description VARCHAR,
departmentid INTEGER,
);
CREATE TABLE IF NOT EXISTS users (
 id int PRIMARY KEY auto_increment,
username VARCHAR,
departmentid VARCHAR,
role VARCHAR,
);
CREATE TABLE IF NOT EXISTS departments_users (
 id int PRIMARY KEY auto_increment,
 userid INTEGER,
 departmentid INTEGER
);
