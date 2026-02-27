INSERT INTO users (email, password, name, role)
VALUES
('admin@gmail.com', 'admin', 'administrator', 'ROLE_ADMIN'),
('saniya@gmail.com', 'secret', 'saniya', 'ROLE_ADMIN');

INSERT INTO short_urls
(short_key, original_url, created_by, is_private)
VALUES
('rs1Aed', 'https://www.youtube.com/', 1, FALSE);

