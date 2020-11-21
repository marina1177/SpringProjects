FROM openjdk:14

RUN mkdir /usr/src/application
RUN mkdir /usr/src/application/data

COPY build/libs/FStoKafka.jar /usr/src/application
COPY src/main/resources/Docs  /usr/src/application/data

WORKDIR /usr/src/application
ENV DOCKER_FS  /usr/src/application/data

EXPOSE 8080
CMD ["java", "-jar", "FStoKafka.jar"]