FROM openjdk:11.0.10-jre-slim-buster AS builder
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
