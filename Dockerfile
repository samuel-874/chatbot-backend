# Use an official Maven image to build the application
FROM maven:3.8.6-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and download the dependencies
COPY pom.xml .

# Download dependencies (this will be cached unless the pom.xml changes)
RUN mvn dependency:go-offline -B

# Copy the source code into the container
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the jar file from the build image
COPY --from=build /app/target/sammychatbot-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application runs on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
