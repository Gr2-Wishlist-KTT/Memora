drop database if exists memora;
create database memora;
use memora;

create table Profile(
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
                         foreign key (owner) references Profile (id) on delete cascade
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

create table Shared_wishlist(
                                id int auto_increment,
                                wishlist_id int not null,
                                shared_with_user_id int not null,
                                primary key(id),
                                foreign key (wishlist_id) references Wishlist (id) on delete cascade,
                                foreign key (shared_with_user_id) references Profile (id) on delete cascade,
                                constraint unique_share UNIQUE (wishlist_id, shared_with_user_id)
);

CREATE TABLE Wish_reservation (
                                  id INT AUTO_INCREMENT,
                                  wish_id INT NOT NULL,
                                  reserved_by_user_id INT NOT NULL,
                                  PRIMARY KEY (id),
                                  FOREIGN KEY (wish_id) REFERENCES Wish(id) ON DELETE CASCADE,
                                  FOREIGN KEY (reserved_by_user_id) REFERENCES Profile(id) ON DELETE CASCADE,
                                  CONSTRAINT unique_reservation UNIQUE (wish_id)
);
