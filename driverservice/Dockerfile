# Use the official OpenJDK 8 image as a base image
FROM openjdk:8-jdk-alpine

# the JAR file path
ARG JAR_FILE=build/libs/driverservice-0.0.1-SNAPSHOT.jar

# Copy the JAR file from the build context into the Docker image
COPY ${JAR_FILE} application.jar

CMD apt-get update -y

# Set the default command to run the Java application
ENTRYPOINT ["java", "-Xmx2048M", "-jar", "/application.jar"]