FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src/main ./src/main
RUN mvn clean package

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/scheduler-0.0.1.jar /app/scheduler.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app/scheduler.jar"]