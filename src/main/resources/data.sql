INSERT INTO user_tbl (EMAIL, FIRST_NAME, LAST_NAME, PASS_WORD, ROLE_ID, USER_NAME) VALUES (
'flodev@gmail.com', 'Eric', 'Florence', '$2a$10$2yhPCSH8dCFS0leAii91EeFoD13fODU6Kgk5KBcuIhcLyNizRO9Lq', 1, 'flodev'
);

INSERT INTO products(product_price, product_name, product_description, discount_percentage, featured_product, current_stock) VALUES
(5.99, 'black_hat', 'A classy top in Dark Mode.', 0, true, 30),
(10.00,'black_shirt', 'Show your Revature pride, now in Dark Mode.', 0, true, 20),
(4.50,'bound_notebook', 'A handy journal for all your pen on paper needs.', 0.05, true, 10),
(3.99, 'carabiner', 'An accessory to wow your friends with your outdoorsiness.', 0, true, 30),
(7.00, 'drawstring_bag', 'A casual container universally used for swim and sports wear.', 0, false, 20),
(5.00, 'gray_hat', 'A classy top.', 0, false, 10),
(10.72, 'gray_shirt', 'Honor Bob Ross as you float around looking like a happy little cloud.', 0.2, false, 30),
(3.51, 'koozie', 'Keep it cool with this koozie', 0, false, 20),
(4.99, 'mug', 'A container to hold drinks.', 0, false, 15),
(3.12, 'pen', 'Limited only by your imagination and the 2nd dimension.', .65, false, 45),
(20.00, 'powerbank', 'A useful tool to add to your utility belt.', 0, false, 50),
(4.15, 'stickers', 'Laptops, Hydro Flasks and Binders. Oh my!', 0, false, 35),
(5.30, 'sticky_notepad', 'Flexible, light weight, agile. The leather armor of the notepad world.', 0, false, 45),
(3.99, 'stress_ball', 'A tool for training your grip strength.', 0, false, 30);
