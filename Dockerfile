# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests


FROM gcr.io/distroless/java21

WORKDIR /app

COPY --from=builder /app/target/portfolio-0.0.1-SNAPSHOT.jar portfolioapis.jar

ENTRYPOINT ["java", "-jar", "/app/portfolioapis.jar"]