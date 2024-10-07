# Presumptions / Decisions

I have made a number of presumptions about the task which I will highlight here

### The system will use PostgresDB to back it

- It is the database I'm currently most familiar with
- The database will be managed with the app via flyway

### I will not implement some code security features present in production code

- In a production grade application, knowing where it will be deployed to, I would include things like holding secrets in a native secrets store, such as AWS Secrets Manager, or preferably using role based access such as RDS IAM Roles to access the database

### There are potentially multiple active polls

- Each of these polls must have a unique name
- A new poll can have the same name as a previously active poll, if it is now inactive

### Each vote must be cast with some form of unique identifier

- In a production system, I would expect us to use something like session/logins/cookies to validate the users
- Each user can only vote once per poll

I have therefore decided to include an email field as a method of creating unique votes for this task, however would not recommend this in production.

### There is a possibility to add new options to a poll

- I do not believe we should be building a non-extensible system, and therefore the system should handle the ability to add new options after creation.

