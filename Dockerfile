FROM eclipse-temurin:17-jdk-alpine as builder

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

ARG JVM_OPTS=""

ENTRYPOINT exec sh -c "java $JVM_OPTS -jar /app/app.jar"
