-- V1__init.sql

CREATE TABLE notifications (
                               id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                               user_id         UUID        NOT NULL,
                               user_email      VARCHAR(255) NOT NULL,
                               event_type      VARCHAR(50)  NOT NULL,  -- ORDER_PLACED | ORDER_STATUS_CHANGED | etc.
                               payload         JSONB        NOT NULL,  -- full event payload for audit
                               channel         VARCHAR(20)  NOT NULL DEFAULT 'EMAIL',
                               status          VARCHAR(20)  NOT NULL DEFAULT 'PENDING',
    -- PENDING | SENT | FAILED
                               retry_count     INTEGER      NOT NULL DEFAULT 0,
                               error_message   TEXT,
                               created_at      TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
                               sent_at         TIMESTAMPTZ
);

CREATE INDEX idx_notifications_user_id    ON notifications(user_id);
CREATE INDEX idx_notifications_status     ON notifications(status);
CREATE INDEX idx_notifications_event_type ON notifications(event_type);