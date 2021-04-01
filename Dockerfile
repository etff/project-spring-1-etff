
FROM openjdk:11.0.10-jre-slim-buster AS builder
COPY . .

FROM openjdk:11.0.10-jre-slim-buster
COPY --from=builder build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
