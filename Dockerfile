FROM openjdk:11-jdk-buster
ADD /com-23people-app-0.0.1-SNAPSHOT.jar //
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/com-23people-app-0.0.1-SNAPSHOT.jar"]
