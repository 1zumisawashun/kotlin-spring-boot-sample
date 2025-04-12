DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users (
    id SERIAL,
    name VARCHAR(255)
);

INSERT INTO
    users(name)
VALUES
    ('東京太郎')
    , ('神奈川花子')
;