create database memora;

create table User(
                     id int auto_increment,
                     username varchar(50) not null,
                     password varchar(100) not null,
                     email varchar(254) not null unique,
                     primary key (id)
);

create table Wishlist(
                         id int auto_increment,
                         title varchar(100) not null,
                         owner int not null,
                         primary key (id),
                         foreign key (owner) references User (id) on delete cascade
);

create table Wish(
                     id int auto_increment,
                     product_name varchar(100) not null,
                     link varchar(2048),
                     description varchar(1000),
                     quantity int,
                     price decimal(8,2),
                     wishlist_id int,
                     primary key(id),
                     foreign key (wishlist_id) references Wishlist (id) on delete cascade
);