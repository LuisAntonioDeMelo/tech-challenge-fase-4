# Stage 1: Build
FROM eclipse-temurin:17-jdk-alpine as build

WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Create a non-root user to run the application
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy the jar file from the build stage
COPY --from=build /workspace/app/target/eureka-server-*.jar app.jar

# Add metadata labels
LABEL maintainer="Tech Challenge Team" \
      version="1.0" \
      description="Eureka Server for Service Discovery"

# Environment variables for JVM tuning
ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseG1GC"

# Add health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
  CMD wget --quiet --tries=1 --spider http://localhost:8761/actuator/health || exit 1

EXPOSE 8761

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
