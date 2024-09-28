# Stage 1: Build the application using Maven
FROM maven:3.8.7-eclipse-temurin-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and any other necessary files for dependencies resolution
COPY pom.xml .

# Copy the rest of the application source code
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the packaged application from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port (change if necessary)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]

