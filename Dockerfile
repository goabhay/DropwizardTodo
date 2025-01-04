FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/proj1-1.0-SNAPSHOT.jar app.jar
COPY config.yml config.yml

ENTRYPOINT ["java", "-jar", "/app/app.jar", "server", "/app/config.yml"]