

FROM maven:3.9-amazoncorretto-21-debian AS build

WORKDIR /app
COPY pom.xml .
COPY src/ ./src/

RUN mvn package -DskipTests

FROM amazoncorretto:25.0.0-alpine3.21
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

