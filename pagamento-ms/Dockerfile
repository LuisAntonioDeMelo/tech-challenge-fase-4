FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /workspace/app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY --from=build /workspace/app/target/pagamento-ms-*.jar app.jar
LABEL maintainer="Tech Challenge Team" version="1.0" description="Payment Microservice"
ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseG1GC"
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 CMD wget --quiet --tries=1 --spider http://localhost:8080/actuator/health || exit 1
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
