INSERT INTO Profile (username, password, email)
VALUES ('alice', 'pw1', 'alice@example.com'),
       ('bob', 'pw2', 'bob@example.com'),
       ('charlie', 'pw3', 'charlie@example.com');

INSERT INTO Wishlist (title, owner)
VALUES ('Alices Fødselsdagsliste', 1),
       ('Alices Juleliste', 1),
       ('Bobs Ønskeliste', 2),
       ('Charlies Ønskeliste', 3);

INSERT INTO Wish (product_name, link, description, quantity, price, wishlist_id)
VALUES ('AirPods Pro', 'https://apple.com/airpods-pro', 'Støjreducerende høretelefoner', 1, 1999.00, 1),
       ('Yoga måtte', 'https://sport.dk/yogamat', 'God til træning', 1, 249.00, 1),
       ('Instant Pot', 'https://kitchen.dk/instantpot', 'Multicooker', 1, 899.00, 2),
       ('Strikket tæppe', NULL, 'Blødt og varmt', 1, 299.00, 2),
       ('Gaming mus', 'https://logitech.com/g502', 'Logitech G502', 1, 499.00, 3),
       ('Kontorstol', NULL, 'Ergonomisk stol', 1, 1299.00, 3),
       ('Løbesko', 'https://nike.com/run', 'Nike løbesko', 1, 899.00, 4),
       ('Termoflaske', NULL, 'Holder vand koldt', 1, 199.00, 4);

INSERT INTO Shared_wishlist (wishlist_id, shared_with_user_id)
VALUES (1, 2),
       (1, 3);

INSERT INTO Wish_reservation (wish_id, reserved_by_user_id)
VALUES (1, 2);
