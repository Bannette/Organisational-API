
CREATE DATABASE apis;
\c apis;
CREATE TABLE departments (
 id SERIAL PRIMARY KEY ,
departmentname VARCHAR,
description VARCHAR,
numberofemployees VARCHAR
);

CREATE TABLE news (
 id SERIAL PRIMARY KEY ,
content VARCHAR,
description VARCHAR,
departmentid INTEGER
);

CREATE TABLE users (
 id SERIAL PRIMARY KEY,
username VARCHAR,
departmentid VARCHAR,
role VARCHAR
);

CREATE TABLE  departments_users (
 id SERIAL PRIMARY KEY,
 userid INTEGER,
 departmentid INTEGER
);

CREATE DATABASE apis_test WITH TEMPLATE apis;
