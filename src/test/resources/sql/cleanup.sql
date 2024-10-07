DELETE FROM dizplai.vote;
DELETE FROM dizplai.options;
DELETE FROM dizplai.poll;

alter sequence dizplai.options_options_id_seq restart with 1;
alter sequence dizplai.poll_poll_id_seq restart with 1;
alter sequence dizplai.vote_vote_id_seq restart with 1;
