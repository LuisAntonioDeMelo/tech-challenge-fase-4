FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/cliente-ms-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
