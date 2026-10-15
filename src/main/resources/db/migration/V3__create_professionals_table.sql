CREATE TABLE professionals (
    id          BIGINT PRIMARY KEY,
    category_id BIGINT,
    first_name  VARCHAR(255),
    last_name   VARCHAR(255),
    enabled     BOOLEAN NOT NULL,
    created_at  TIMESTAMP(6) WITH TIME ZONE,
    updated_at  TIMESTAMP(6) WITH TIME ZONE,

    CONSTRAINT fk_professionals_category FOREIGN KEY (category_id) REFERENCES categories (id)
);
