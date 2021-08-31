CREATE TABLE IF NOT EXISTS company
(
    id   SERIAL PRIMARY KEY,
    name varchar UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS staff
(
    id         INT PRIMARY KEY,
    company_id INT            NOT NULL,
    first_name varchar        NOT NULL,
    last_name  varchar        NOT NULL,
    nick_name  varchar UNIQUE NOT NULL,
    CONSTRAINT fk_company_id
        FOREIGN KEY (company_id)
            REFERENCES company (id)
);

CREATE TABLE IF NOT EXISTS salary
(
    id       SERIAL PRIMARY KEY,
    staff_id INT     NOT NULL,
    currency varchar NOT NULL,
    value    INT     NOT NULL,
    CONSTRAINT fk_staff_id
        FOREIGN KEY (staff_id)
            REFERENCES staff (id)
);

