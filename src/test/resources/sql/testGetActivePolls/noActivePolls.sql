alter sequence dizplai.poll_poll_id_seq restart with 1;

INSERT INTO dizplai.poll
(name, description, created_date, end_date)
VALUES('Bens poll', 'a description', CURRENT_DATE - 2, CURRENT_DATE - 1);

