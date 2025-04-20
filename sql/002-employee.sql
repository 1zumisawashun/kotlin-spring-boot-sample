DROP TABLE IF EXISTS employee;

CREATE TABLE IF NOT EXISTS employee (
    id SERIAL,
    name VARCHAR(255)
);

INSERT INTO
    employee(name)
VALUES
    ('東京太郎')
    , ('神奈川花子')
;