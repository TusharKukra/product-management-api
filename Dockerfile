# Stage 1: Build the application
FROM maven:3.9.4-eclipse-temurin-17 AS builder

# Set the working directory for the build
WORKDIR /app

# Copy only necessary files for dependency resolution
COPY pom.xml .

# Pre-fetch dependencies
RUN mvn dependency:go-offline -B

# Copy the source code and other required files
COPY src ./src
COPY public/images ./images

# Build the application JAR file
RUN mvn clean package -DskipTests

# Stage 2: Create a lightweight runtime image
FROM openjdk:17-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage to the runtime stage
COPY --from=builder /app/target/product-management-api-0.0.1-SNAPSHOT.jar app.jar

# Copy static resources (if required for runtime)
COPY --from=builder /app/images ./public/images

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
