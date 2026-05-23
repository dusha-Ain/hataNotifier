--liquibase formatted sql

--changeset create:1
CREATE TABLE outbox_messages
(
    id           UUID PRIMARY KEY,
    aggregate_id VARCHAR(255) NOT NULL,
    type         VARCHAR(255) NOT NULL,
    payload      JSONB        NOT NULL,
    created_at   TIMESTAMP DEFAULT NOW(),
    processed_at TIMESTAMP
);

--changeset create:2
CREATE INDEX idx_outbox_unprocessed ON outbox_messages (processed_at) WHERE processed_at IS NULL;