CREATE TABLE IF NOT EXISTS  company (
    id SERIAL PRIMARY  KEY,
    name VARCHAR (128) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS employee (
    id SERIAL PRIMARY  KEY,
    first_name VARCHAR (128) NOT NULL,
    last_name VARCHAR (128) NOT NULL,
    birthday DATE,
    salary INTEGER NOT NULL,
   company_id INTEGER REFERENCES company(id)
);

CREATE TABLE IF NOT EXISTS  users (id SERIAL PRIMARY  KEY,
                                    name VARCHAR (128) NOT NULL,
                                salary INTEGER NOT NULL
);