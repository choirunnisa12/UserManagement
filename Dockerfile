FROM openjdk:21-jdk-alpine
COPY target/user-management.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

