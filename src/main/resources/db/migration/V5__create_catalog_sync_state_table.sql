CREATE TABLE catalog_sync_state (
    id         BIGINT PRIMARY KEY,
    version    BIGINT NOT NULL,
    updated_at TIMESTAMP(6) WITH TIME ZONE NOT NULL
);
