# Build Stage
FROM gradle:8-jdk22-alpine AS builder

# Set working directory and copy source files
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# Build the project, excluding tests
RUN gradle build -x test

# Package Stage
FROM openjdk:22-jdk

# Create application directory in the container
RUN mkdir /app

# Copy the built JAR file from the build stage to the final image
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/inventory-service.jar

# Set environment variables for database credentials
ENV POSTGRES_USER=rootadmin \
    POSTGRES_PASSWORD=password

# Expose the port the service will run on
EXPOSE 8086

# Run the application
ENTRYPOINT ["java", "-jar", "/app/inventory-service.jar"]
