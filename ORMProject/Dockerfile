FROM openjdk:14
RUN mkdir /usr/src/hola
COPY build/libs/ormproject.jar /usr/src/orm
WORKDIR /usr/src/hola
EXPOSE 8080
ENV DOCKER_FS  Docs
CMD ["java", "-jar", "ormproject.jar"]