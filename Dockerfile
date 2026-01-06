# ===== STAGE 1: BUILD =====
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copia apenas arquivos necessários para resolver dependências
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

RUN ./mvnw -B dependency:go-offline

# Copia o restante do código
COPY src src

RUN ./mvnw -B clean package -DskipTests


# ===== STAGE 2: RUNTIME =====
FROM eclipse-temurin:21-jre

WORKDIR /app

EXPOSE 8080

COPY --from=build /app/target/registros-1.0.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]