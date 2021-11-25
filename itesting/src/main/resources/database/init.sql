CREATE TABLE tz_orders (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    description VARCHAR(50),
    created TIMESTAMP DEFAULT current_timestamp
);