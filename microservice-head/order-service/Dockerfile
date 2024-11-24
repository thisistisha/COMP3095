# docker build -t order-service:1.0 .
# Build Stage
FROM gradle:8-jdk22-alpine AS builder

# Set working directory and copy source files
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# Build the project and exclude tests
RUN gradle build -x test

# Package Stage
FROM openjdk:22-jdk

# Create application directory
RUN mkdir /app

# Copy built JAR from the builder stage to the app directory
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/order-service.jar

# Environment variables (adjust based on your application requirements)
ENV POSTGRES_DB=orderservice \
    POSTGRES_USER=rootadmin \
    POSTGRES_PASSWORD=password

# Expose the application port
EXPOSE 8085

# Entry point for running the application
ENTRYPOINT ["java", "-jar", "/app/order-service.jar"]
