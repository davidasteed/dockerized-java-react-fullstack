Development notes as of 31 March 2024:
- local development phase is completed for the initial build
- TODO:  unit tests and code coverage
- The SupervisorNotification microservice will be responsible for consolidating the list of current supervisors as well as updating the notification service

Endpoints:
- GET /api/supervisors
- POST /api/submit

To run locally:
- git checkout the repo
- mvnw clean install
- launch via run configuration from IDE (such as Eclipse), or as a standalone java application:
-	cd ./target
-	java -jar SupervisorNotification-0.0.1-SNAPSHOT.jar

To run locally as a docker image:
- docker-compose build
- docker-compose up

