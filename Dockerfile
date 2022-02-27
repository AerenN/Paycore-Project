FROM openjdk:18-ea-11-jdk-alpine

MAINTAINER "Ali Eren Baysal"

# Spring Boot Embedded Tomcat Port: 8080
# Remote debugging port: 5005
EXPOSE 8080 5005
ADD target/CreditCalculator-0.0.1-SNAPSHOT.jar CreditCalculator.jar

ENTRYPOINT ["java","-jar","CreditCalculator.jar"]


