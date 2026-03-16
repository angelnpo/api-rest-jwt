-- Insert users
INSERT INTO api_rest_jwt.users(username, first_name, last_name, email, password, role)
VALUES ('admin', 'Joe', 'Dustin', 'admin@mail.com', '$2a$10$EeDoXQx6ykGBp2.F11d24ulk9C.GWK82pWhcdaKxUeOran511EzQa', 'ADMIN'),
('user', 'Niko', 'Doe', 'user@mail.com', '$2a$10$EeDoXQx6ykGBp2.F11d24ulk9C.GWK82pWhcdaKxUeOran511EzQa', 'USER')
ON CONFLICT DO NOTHING;
