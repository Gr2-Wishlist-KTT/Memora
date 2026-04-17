
drop all objects;
-- drop table if exists Wish;
-- drop table if exists Wishlist;
-- drop table if exists Profile;

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


INSERT INTO Profile (username, password, email) VALUES
                                                 ('anna', 'anna_123', 'anna@test.com'),
                                                 ('mads', 'mads_321', 'mads@test.com');


INSERT INTO Wishlist (title, owner) VALUES
                                        ('Annas Fødselsdagsliste', 1),
                                        ('Mads – Juleønsker', 2),
                                        ('Annas Hobbyting', 1);


INSERT INTO Wish (product_name, link, description, quantity, price, wishlist_id) VALUES
                                                                                     ('AirPods Pro', 'https://apple.com/airpods-pro', 'Nye høretelefoner til træning', 1, 1899.00, 1),
                                                                                     ('Yoga måtte', NULL, 'En tyk og skridsikker måtte', 1, 299.00, 1),
                                                                                     ('PS5 Controller', 'https://example.com/ps5-controller', 'Ekstra controller til multiplayer', 1, 499.00, 2),
                                                                                     ('Kaffekværn', 'https://example.com/coffee-grinder', 'Til friskmalede bønner', 1, 349.00, 2),
                                                                                     ('Akrylmaling sæt', 'https://example.com/acrylic-paint', '24 farver', 1, 179.00, 3),
                                                                                     ('Skitsebog A4', NULL, 'Tykt papir, 120 sider', 1, 89.00, 3),
                                                                                     ('Penselsæt', 'https://example.com/brush-set', '10 stk forskellige størrelser', 1, 129.00, 3);