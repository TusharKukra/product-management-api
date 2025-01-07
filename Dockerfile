# Use a Maven image to build the application
FROM maven:3.9.4-eclipse-temurin-17 AS builder

# Set the working directory for the build
WORKDIR /app

# Copy the Maven project files to the container
COPY pom.xml .
COPY src ./src
COPY public ./public

# Build the application JAR file
RUN mvn clean package -DskipTests

# Use an OpenJDK base image for running the application
FROM openjdk:17

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage to the runtime stage
COPY --from=builder /app/target/product-management-api-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
