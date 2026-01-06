# ===== STAGE 1: BUILD =====
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

RUN apt-get update && apt-get install -y maven

COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src src

RUN mvn -B clean package -DskipTests


# ===== STAGE 2: RUNTIME =====
FROM eclipse-temurin:21-jre

WORKDIR /app

EXPOSE 8080

COPY --from=build /app/target/qrcode-spring-1.0.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]