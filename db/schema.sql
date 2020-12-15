CREATE TABLE post (
     id SERIAL PRIMARY KEY,
     name TEXT,
     description TEXT,
     created TIMESTAMP
);
CREATE TABLE candidate (
     id SERIAL PRIMARY KEY,
     name TEXT,
     photoId INTEGER
);
CREATE TABLE photo (
    id SERIAL PRIMARY KEY,
    name TEXT
);
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name TEXT,
    email TEXT,
    password TEXT
);