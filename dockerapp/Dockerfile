FROM openjdk:14

#FROM prom/prometheus:latest
#ADD prometheus/* /etc/prometheus/

RUN mkdir /usr/src/application

COPY build/libs/dockerapp.jar /usr/src/application
#COPY build/libs/dockerapp.jar app.jar

WORKDIR /usr/src/application

CMD ["java", "-jar", "dockerapp.jar"]