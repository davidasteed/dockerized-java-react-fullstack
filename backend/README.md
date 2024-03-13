# lightfeather-backend

The SupervisorNotification microservice will be responsible for consolidating the list of current supervisors as well as updating the
notification service

Endpoints:

GET /api/supervisors

POST /api/submit




To run locally:

a)  git checkout the repo

b)  mvnw clean install

c)  launch via run configuration from IDE (such as Eclipse), or as a standalone java application:
	cd ./target
	java -jar SupervisorNotification-0.0.1-SNAPSHOT.jar



To deploy and run a docker image:

a)  git checkout the repo 

b)  docker build -t lightfeather-backend .

c)  docker run -p 8080:8080 lightfeather-backend

