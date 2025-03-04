FROM openjdk:23-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/CGI-Project_Backend-0.0.1-SNAPSHOT.jar app.jar
# Expose the necessary port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
