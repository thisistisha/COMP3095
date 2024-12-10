# Build Stage
FROM gradle:8-jdk22-alpine AS builder

# Copy source code and set working directory
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# Build the project without running tests
RUN gradle build -x test --info

# Package Stage
FROM openjdk:22-jdk

# Create an app directory
RUN mkdir /app

# Copy the JAR file generated from the build stage
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/notification-service.jar

EXPOSE 8082

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/notification-service.jar"]
