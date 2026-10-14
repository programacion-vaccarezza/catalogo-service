CREATE TABLE weekly_schedules (
    id                    BIGINT PRIMARY KEY,
    professional_id       BIGINT,
    day_of_week           VARCHAR(255),
    start_time            TIME(0) WITHOUT TIME ZONE,
    end_time              TIME(0) WITHOUT TIME ZONE,
    slot_duration_minutes INTEGER NOT NULL,
    enabled               BOOLEAN NOT NULL,
    created_at            TIMESTAMP(6) WITH TIME ZONE,
    updated_at            TIMESTAMP(6) WITH TIME ZONE,

    CONSTRAINT fk_weekly_schedules_professional FOREIGN KEY (professional_id) REFERENCES professionals (id),
    CONSTRAINT chk_weekly_schedules_day_of_week CHECK (day_of_week IN
        ('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY'))
);
