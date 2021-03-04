#Download node docker image java
#Build app#

# RUN stage
#
FROM openjdk:11-jre-slim
COPY target/*.jar /app/credit-service.jar
WORKDIR /app
CMD ["java","-jar","/app/credit-service.jar"]
