CREATE TABLE IF NOT EXISTS dizplai.poll (
    poll_id INTEGER GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(64) NOT NULL,
    description VARCHAR(256),
    created_date DATE NOT NULL,
    end_date DATE,
    primary key(poll_id)
);