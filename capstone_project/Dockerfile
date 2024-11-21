# Use an OpenJDK image as the base
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy your JAR file from the target directory to the container
COPY target/capstone-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that your Spring Boot application will run on (default is 8080)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
