INSERT INTO user_tbl (EMAIL, FIRST_NAME, LAST_NAME, PASS_WORD, ROLE_ID, USER_NAME) VALUES (
'flodev@gmail.com', 'Eric', 'Florence', '$2a$10$2yhPCSH8dCFS0leAii91EeFoD13fODU6Kgk5KBcuIhcLyNizRO9Lq', 1, 'flodev'
);
INSERT INTO product_tbl (product_price, product_name, product_description, discount_percentage, featured_product, current_stock) VALUES (
5, 'black_hat', 'A black hat to commemorate your joining the dark side.', 0, true, 30),
(10,'black_shirt', 'A stylish option if you want your torso to channel the void.', 0, true, 20),
(4,'bound_notebook', 'A handy journal for all your pen on paper needs.', 0, true, 10),
(3, 'carabiner', 'An accessory to wow your friends with your outdoorsiness.', 0, true, 30),
(7, 'drawstring_bag', 'A casual container universally used for swim and sports wear.', 0, false, 20),
(5, 'gray_hat', "A classy top to reflect your brain's permanent state of indecision", 0, false, 10),
(10, 'gray_shirt', 'Honor Bob Ross as you float around looking like a happy little cloud.', 0, false, 30),
(3, 'koozie', "YOU KNOW WHAT IT'S FOR, HOLLA!", 0, false, 20),
(4, 'mug', 'A container to hold drinks, SOUP IS NOT A DRINK.', 0, false, 15),
(3, 'pen', 'Limited only by your imagination and the 2nd dimension.', 0, false, 45),
(20, 'powerbank', 'When your phone dies the night is over, keep the party going as the most prepared raver.', 0, false, 50),
(4, 'stickers', 'Laptops, Hydro Flasks and Binders. Oh my!', 0, false, 35),
(5, 'sticky_notepad', 'Flexible, light weight, agile. The leather armor of the notepad world.', 0, false, 45),
(3, 'stress_ball', "The swag I need after this 10 weeks.", 0, false, 30
);