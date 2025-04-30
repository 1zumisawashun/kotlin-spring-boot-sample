CREATE TABLE users (
   id SERIAL PRIMARY KEY,
   email VARCHAR(256) UNIQUE NOT NULL,
   password VARCHAR(128) NOT NULL,
   name VARCHAR(32) NOT NULL,
   role_type VARCHAR(10) NOT NULL
);

CREATE TABLE book (
   id SERIAL PRIMARY KEY,
   title VARCHAR(128) NOT NULL,
   author VARCHAR(32) NOT NULL,
   release_date DATE NOT NULL
);

CREATE TABLE rental (
   book_id INT NOT NULL,
   user_id INT NOT NULL,
   rental_datetime TIMESTAMP NOT NULL,
   return_deadline TIMESTAMP NOT NULL,
   PRIMARY KEY (book_id, user_id),
   FOREIGN KEY (book_id) REFERENCES book(id),
   FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO book (id, title, author, release_date)
VALUES
   (100, 'Kotlin入門', 'コトリン太郎', '1950-10-01'),
   (200, 'Java入門', 'ジャヴァ太郎', '2005-08-29');

INSERT INTO users (id, email, password, name, role_type)
VALUES
   (1, 'admin@test.com', '$2a$10$klkv2YEQVevs1LsFHzXeVeJBg3tAkTmt0/pSTBI7pT7QsUy9ijhHK', '管理者', 'ADMIN'),
   (2, 'user@test.com', '$2a$10$dtB.bySf.ZcbOPOp3Q7ZgedqofClN56rQ6JboxBuiW02twNMcAoZS', 'ユーザー', 'USER');