FROM openjdk:21-jdk

WORKDIR /app

COPY ./target/usuarios-0.0.1-SNAPSHOT.jar .

EXPOSE 8111

ENTRYPOINT ["java", "-jar", "usuarios-0.0.1-SNAPSHOT.jar"]
