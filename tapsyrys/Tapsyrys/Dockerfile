# Stage 1: Сборка (используем Maven с JDK 17)
FROM maven:3.8.5-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Запуск (используем стабильный JRE 17)
FROM eclipse-temurin:17-jre-slim
COPY --from=build /target/*.jar app.jar

# Ограничиваем память, чтобы Render не убил процесс (512MB RAM limit)
ENTRYPOINT ["java", "-Xmx300m", "-Xss512k", "-jar", "/app.jar"]