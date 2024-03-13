FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /tmp/
COPY pom.xml /tmp/
COPY src /tmp/src/
RUN mvn package

FROM openjdk:17-jdk-slim
ARG JAR_FILE=/tmp/target/*.jar
COPY --from=build ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]