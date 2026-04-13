drop table if exists memora;

create database memora;

       use memora;

create table user (
    id int auto_increment primary key,
    username varchar(100) unique,
    password varchar(100),
    email varchar(100)
);