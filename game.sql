CREATE TABLE game (
    id serial NOT NULL,
    name text NOT NULL,
    release_date date NOT NULL,
    developer text,
    score smallint
);
