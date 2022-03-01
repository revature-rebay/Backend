INSERT INTO user_tbl (EMAIL, FIRST_NAME, LAST_NAME, PASS_WORD, ROLE_ID, USER_NAME) VALUES (
'alex@sample.com', 'Alex', 'Anderson', '$2a$10$2yhPCSH8dCFS0leAii91EeFoD13fODU6Kgk5KBcuIhcLyNizRO9Lq', 1, 'WindowShopper'
);

INSERT INTO user_tbl (EMAIL, FIRST_NAME, LAST_NAME, PASS_WORD, ROLE_ID, USER_NAME) VALUES (
'sam@sample.com', 'Sam', 'Simpson', '$2a$10$2yhPCSH8dCFS0leAii91EeFoD13fODU6Kgk5KBcuIhcLyNizRO9Lq', 1, 'PackRat'
);

INSERT INTO user_tbl (EMAIL, FIRST_NAME, LAST_NAME, PASS_WORD, ROLE_ID, USER_NAME) VALUES (
'jordan@rebay.net', 'Jordan', 'Jones', '$2a$10$2yhPCSH8dCFS0leAii91EeFoD13fODU6Kgk5KBcuIhcLyNizRO9Lq', 2, 'BigBoss'
);

INSERT INTO products (product_price, product_name, product_description, discount_percentage, featured_product, current_stock, product_image)
VALUES (3.50, 'Snacky', 'This snacky has CHOCOLATE', 0.2, 'true', 25, null),
(9.99, 'Movie', 'This movie has Hugh Jackman', 0.0, 'false', 25, null);