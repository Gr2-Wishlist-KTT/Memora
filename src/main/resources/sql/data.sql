use memora;

INSERT INTO Profile (username, password, email) VALUES
                                                 ('anna', 'hashed_pw_anna', 'anna@example.com'),
                                                 ('mads', 'hashed_pw_mads', 'mads@example.com');


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