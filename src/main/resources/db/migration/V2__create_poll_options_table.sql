CREATE TABLE IF NOT EXISTS dizplai.options (
    options_id INTEGER GENERATED ALWAYS AS IDENTITY,
    poll_id INTEGER,
    name VARCHAR(256),
    PRIMARY KEY(options_id),
    CONSTRAINT fk_poll FOREIGN KEY(poll_id) REFERENCES dizplai.poll(poll_id) on delete cascade
);