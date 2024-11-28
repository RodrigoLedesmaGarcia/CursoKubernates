FROM alpine/java:21.0.2-jdk

WORKDIR /app

COPY ./target/cursos-0.0.1-SNAPSHOT.jar .

EXPOSE 8112

ENTRYPOINT ["java", "-jar", "cursos-0.0.1-SNAPSHOT.jar"]