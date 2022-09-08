FROM openjdk:17-jdk-alpine
MAINTAINER rizki mufrizal

ADD target/*.jar app.jar
RUN sh -c 'touch /app.jar'