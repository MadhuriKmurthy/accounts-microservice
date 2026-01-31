#Base image containing Java runtime
FROM openjdk:21-ea-21-jdk-slim

#Info around who maintains the image
MAINTAINER madhuri

#Our code
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

#When one is creating the docker container, execute below command
ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]