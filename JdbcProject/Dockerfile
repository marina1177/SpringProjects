FROM openjdk:14
RUN mkdir /usr/src/jdbc
COPY build/libs/JdbcProject.jar /usr/src/jdbc
WORKDIR /usr/src/jdbc
EXPOSE 8080
CMD ["java", "-jar", "jdbcproject.jar"]