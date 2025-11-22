-- Cоздаём таблицу для машин
CREATE TABLE cars (
    car_id SERIAL PRIMARY KEY,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
);
-- Создаём таблицу для персон
CREATE TABLE persons (
    person_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INTEGER NOT NULL CHECK (age >= 0 AND age <= 100),
    has_license BOOLEAN NOT NULL DEFAULT false,
    car_id INTEGER REFERENCES cars(car_id) ON DELETE SET NULL,
);