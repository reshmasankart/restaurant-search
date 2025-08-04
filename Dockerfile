# Dockerfile for a Java application using Eclipse Temurin Image
FROM eclipse-temurin:21-jre-alpine-3.20
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]