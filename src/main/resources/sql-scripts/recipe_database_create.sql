-- PostgreSQL Schema DDL for recipe_prod

CREATE TABLE IF NOT EXISTS category (
    id BIGSERIAL PRIMARY KEY,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS unit_of_measure (
    id BIGSERIAL PRIMARY KEY,
    description VARCHAR(255),
    uom VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS notes (
    id BIGSERIAL PRIMARY KEY,
    recipe_notes TEXT,
    recipe_id BIGINT
);

CREATE TABLE IF NOT EXISTS recipe (
    id BIGSERIAL PRIMARY KEY,
    cook_time INTEGER,
    description VARCHAR(255),
    difficulty VARCHAR(255),
    directions TEXT,
    image BYTEA,
    prep_time INTEGER,
    servings INTEGER,
    source VARCHAR(255),
    url VARCHAR(255),
    notes_id BIGINT REFERENCES notes(id)
);

CREATE TABLE IF NOT EXISTS ingredient (
    id BIGSERIAL PRIMARY KEY,
    amount NUMERIC(19, 2),
    description VARCHAR(255),
    recipe_id BIGINT REFERENCES recipe(id),
    uom_id BIGINT REFERENCES unit_of_measure(id)
);

CREATE TABLE IF NOT EXISTS recipe_category (
    recipe_id BIGINT NOT NULL REFERENCES recipe(id),
    category_id BIGINT NOT NULL REFERENCES category(id),
    PRIMARY KEY (recipe_id, category_id)
);