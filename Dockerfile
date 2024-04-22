# Stage 1: Build with Maven
FROM maven:3.8.5-openjdk-17-slim AS build

WORKDIR /home/app

COPY src /home/app/src
COPY pom.xml /home/app/

RUN mvn clean package -DskipTests 

# Stage 2: Run with OpenJDK
FROM openjdk:17-alpine

# Copy the JAR file from the build stage to the current directory
COPY --from=build /home/app/target/qp-assessment.jar /tmp/qp-assessment.jar

# application port expose
EXPOSE 8090/tcp

ENTRYPOINT ["java","-jar","/tmp/qp-assessment.jar"]
