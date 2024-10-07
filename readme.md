# Dizplai Coding Challenge

### Code quality

- As of 07/10/2024 at 19:35 there are 2 potentially vulnerable dependencies, both included from the latest Springboot Data JPA
  - JAXB Core / TXW2 - 4.0.5 - No fix to this issue - last update of JAXB Core Mar 07, 2024
- Code coverage of the application is at 88% Class, 93% method, 97% Line and 83% branch according to IntelliJ built in coverage

### Prerequisites / Build

- This project is built with Java 21 and Maven 3.9.9 (or use the maven wrapper provided)
- Pull a new PostgresSQL image `docker pull postgres` as this will be used during build/test
- Please ensure that the following:
  - No docker volumes currently exist called `db_mount`, or rename the mount name/location in `docker-compose.yaml:3`
  - No container named `postgres` or any container/application listening on port `5432`
  - No container/application listening on port `8080`

To build the application/artifacts and run all tests:
- `./mvnw clean verify`
The Spring application will bring up the docker images as required when running locally

You may then run the application locally via your preferred IDE, CLI, or via docker.

As the application is using `spring-boot-docker-compose`, it will attempt to bring up the postgres docker image if running through IDE, but not via CLI or docker.

### Docker Container
- Ensure the artifact has been generated (a jar named `voting-api-0.0.1-SNAPSHOT.jar` exists in the target dir)
  - `./mvnw clean package -DskipTests`
- Build the docker image
  - `docker build -t voting-api .`

By using docker compose profiles, you can bring up both the application and database with `docker compose --profile full up -d`
Please reference the `docker-compose.yaml` file for additional reference

### Application Details
- The project uses flyway to manage its database state
- I have chosen to use a postgres database as I believe it is the easiest 'generic' database to work with, while being highly scalable
- I have chosen to not implement some secret management solution for the database. This is because I would have to know which cloud environment to target.

### Problem presumptions

- I have presumed that the API is fully public. This is because the brief does not mention any unique voting mechanisms. In a production system:
  - If we need to guarantee one vote per user - I would anticipate there to be a login system, with an auth token to validate
  - If we don't need to guarantee, but attempt to stop the easiest manipulation - The client UI could use cookies to set whether they have voted on a particular poll, an Api gateway could be used to restrict IP addresses after votes etc. All easy to circumvent if you are intending on being malicious.
- I have presumed that the API would need to be extensible, and therefore have included a few additional requirements:
  - Multiple polls can run concurrently
  - A poll can add additional options while it is running
- I have included some validation on request objects to stop any database constraints

### Other Notes

- I have not created a UI for this task, as advised by Oli (recruiter)
- Given the brief of the solution, I would love to talk about the companies approach to serverless technologies, as the brief initially screamed a serverless solution
- Although the brief suggested not writing tests for everything, I generally try to use TDD where possible, which lends to the higher code coverage
- Due to completing some of the work across multiple devices, I have had to rebase my project to change the commit authors (an email address tied to Evri)
- The task took around 4 hours, the majority of which was debugging Springs @Sql annotation.

Thank you for giving me to opportunity to complete this. Although not a massive task, I thoroughly enjoyed it.

