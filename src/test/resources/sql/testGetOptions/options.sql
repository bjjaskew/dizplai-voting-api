INSERT INTO dizplai.poll
(name, description, created_date, end_date)
VALUES('Bens poll', 'a description', CURRENT_DATE - 1, CURRENT_DATE + 1);

INSERT INTO dizplai.options
(name, poll_id)
VALUES('option 1', 1);

INSERT INTO dizplai.options
(name, poll_id)
VALUES('option 2', 1);
