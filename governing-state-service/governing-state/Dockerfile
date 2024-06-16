# Use an official OpenJDK runtime as a parent image
FROM openjdk:11

# Set the working directory in the container
WORKDIR /app

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

# Copy the project's pom.xml and source code to the container
COPY pom.xml ./
COPY src ./src

# Build the application
RUN mvn clean install

# Expose the port the application runs on
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "target/governing-state-0.0.1-SNAPSHOT.jar"]