FROM maven:3.6.3-jdk-11 as builder

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml ./
COPY src ./src/

# Build a release artifact.
RUN mvn package -DskipTests

FROM adoptopenjdk/openjdk11:jdk-11.0.6_10-alpine

# Copy the jar to the production image from the builder stage.
COPY --from=builder /app/target/com-23people-app-*.jar /com-23people-app.jar

EXPOSE 8080

# Run the web service on container startup.
CMD ["java","-Djava.security.egd=file:/dev/./urandom", "-jar","/com-23people-app.jar"]
