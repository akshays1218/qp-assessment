FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /home/app
COPY src /home/app/src

COPY pom.xml /home/app/

RUN mvn clean package -DskipTests 

FROM openjdk:17-alpine
COPY --from=0 /home/app/target/qp-assessment.jar /tmp/qp-assessment.jar

EXPOSE 80/tcp
ENTRYPOINT ["java","-jar","/tmp/qp-assessment.jar"]