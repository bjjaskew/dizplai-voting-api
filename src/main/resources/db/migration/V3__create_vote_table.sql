CREATE TABLE IF NOT EXISTS dizplai.vote (
    vote_id INTEGER GENERATED ALWAYS AS IDENTITY,
    poll_id INTEGER,
    options_id Integer,
    vote_date DATE,
    PRIMARY KEY(vote_id),
    CONSTRAINT fk_poll FOREIGN KEY(poll_id) REFERENCES dizplai.poll(poll_id) on delete cascade,
    CONSTRAINT fk_option FOREIGN KEY(options_id) REFERENCES dizplai.options(options_id) on delete cascade
);