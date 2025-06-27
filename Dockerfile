# Build Stage
FROM maven:3.9.5-eclipse-temurin-21-alpine AS build

WORKDIR /app

# Copy pom.xml and go offline
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source
COPY src ./src

# Package the app (skip tests)
RUN mvn clean package -DskipTests

# Runtime Stage
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/doubt-solver-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 5000

ENTRYPOINT ["java", "-jar", "app.jar"]
