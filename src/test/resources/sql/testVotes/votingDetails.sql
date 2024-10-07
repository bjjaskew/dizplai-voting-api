INSERT INTO dizplai.poll
(name, description, created_date, end_date)
VALUES('Bens poll', 'a description', CURRENT_DATE - 1, CURRENT_DATE + 1);

INSERT INTO dizplai.options
(name, poll_id)
VALUES('option 1', 1);

INSERT INTO dizplai.options
(name, poll_id)
VALUES('option 2', 1);

INSERT INTO dizplai.poll
(name, description, created_date, end_date)
VALUES('another poll', 'a second', CURRENT_DATE - 1, CURRENT_DATE + 1);

INSERT INTO dizplai.options
(name, poll_id)
VALUES('option 3', 2);

INSERT INTO dizplai.vote
(poll_id, options_id, vote_date)
VALUES(1, 1, CURRENT_DATE);

INSERT INTO dizplai.vote
(poll_id, options_id, vote_date)
VALUES(1, 1, CURRENT_DATE);

INSERT INTO dizplai.vote
(poll_id, options_id, vote_date)
VALUES(1, 1, CURRENT_DATE);

INSERT INTO dizplai.vote
(poll_id, options_id, vote_date)
VALUES(1, 2, CURRENT_DATE + 1);