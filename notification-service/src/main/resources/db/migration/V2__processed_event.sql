-- Idempotency: track processed event IDs to handle at-least-once delivery
CREATE TABLE processed_events (
                                  event_id    UUID        PRIMARY KEY,
                                  processed_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
