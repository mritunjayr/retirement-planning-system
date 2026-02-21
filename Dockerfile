# docker build -t blk-hacking-ind-mritunjay-kumar .
# Using Eclipse Temurin as base - official OpenJDK distribution with long-term support, optimized for production
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 5477

ENTRYPOINT ["java", "-jar", "app.jar"]
