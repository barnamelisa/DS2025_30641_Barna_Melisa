# Etapa de build
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn dependency:resolve

COPY src /app/src
RUN mvn clean install -DskipTests

# Etapa finală
FROM eclipse-temurin:21-jre-alpine

# Setare nume etapă pentru lizibilitate
# COPY --from=build... este echivalent cu COPY --from=0...
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]