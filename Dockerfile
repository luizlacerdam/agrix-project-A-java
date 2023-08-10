FROM maven:3.8.4-openjdk-11 AS build-image

WORKDIR /to-build-app

COPY src ./src
COPY pom.xml .

RUN mvn dependency:go-offline

RUN mvn package

FROM openjdk:11-jre-slim

WORKDIR /app

COPY --from=build-image /to-build-app/target/*.jar ./app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
