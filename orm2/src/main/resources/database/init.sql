DROP TABLE IF EXISTS tj_car_history_owners;
DROP TABLE IF EXISTS tz_cars;
DROP TABLE IF EXISTS tz_engines;
DROP TABLE IF EXISTS tz_drivers;

DROP SEQUENCE IF EXISTS tj_car_history_owners_id_seq;
DROP SEQUENCE IF EXISTS tz_cars_id_seq;
DROP SEQUENCE IF EXISTS tz_engines_id_seq;
DROP SEQUENCE IF EXISTS tz_drivers_id_seq;

CREATE TABLE tz_engines (
    id SERIAL PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    power INTEGER NOT NULL,
    capacity INTEGER NOT NULL
);

CREATE TABLE tz_drivers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(90) NOT NULL
);

/* Машины связаны отношением "один к одному" с двигателями */

CREATE TABLE tz_cars (
    id SERIAL PRIMARY KEY,
    id_engine INTEGER NOT NULL UNIQUE REFERENCES tz_engines (id) ON DELETE CASCADE,
    name VARCHAR(120) NOT NULL
);

/*
    Технически водители могут быть связаны отношением "многие ко многим" с машинами через данную таблицу,
    но у этой таблицы истории есть собственный id и собственная смысловая нагрузка. Поэтому на уровне
    hibernate мы приобретаем больше функционала, если объявить 2 связи "один ко многим"
    к этой самостоятельной сущности, назвав ее, например, HistoryRecord.
*/

CREATE TABLE tj_car_history_owners (
    id SERIAL PRIMARY KEY,
    id_car INTEGER NOT NULL REFERENCES tz_cars (id) ON DELETE CASCADE,
    id_driver INTEGER NOT NULL REFERENCES tz_drivers (id) ON DELETE CASCADE,
    recDateTime TIMESTAMP DEFAULT current_timestamp
);