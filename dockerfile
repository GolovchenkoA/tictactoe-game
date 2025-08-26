# Start from a lightweight base image with Java
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot jar into the container
COPY build/libs/*.jar app.jar

# Expose the application port (adjust if needed)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
