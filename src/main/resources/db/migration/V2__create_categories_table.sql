CREATE TABLE categories (
    id          BIGINT PRIMARY KEY,
    name        VARCHAR(255),
    description VARCHAR(255),
    enabled     BOOLEAN NOT NULL,
    created_at  TIMESTAMP(6) WITH TIME ZONE,
    updated_at  TIMESTAMP(6) WITH TIME ZONE
);
