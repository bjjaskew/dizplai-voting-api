# Task List

### T1: Configure base project to better fit the task
[X] - T1A - Implement dockerfile to generate container

[X] - T1B - Implement docker compose including the app & connect the app to postgres

[X] - T1C - Implement flyway configuration with the application

### T2: Implement create poll / get polls
[X] - T2A - Poll table design

    Name, description, id, created date, end date

[X] - T2B - Get list of active polls

[X] - T2C - Get list of all polls

[ ] - T2D - Create poll endpoint


### T3: Implement poll options
[ ] - T3A - Poll options table design

    Option name/desc, FK poll, Id

[ ] - T3B - Create poll option/options endpoint

[ ] - T3C - Get poll options endpoint

### T4: Implement vote in pole
[ ] - T3A - Vote table

    email/unique identifier, FK option, ID

[ ] - T4B - Vote endpoint

### T5: Implement view votes
[ ] - T5A - Get endpoint to gather votes by poll option, on poll id



