# Stage 1: Сборка
FROM maven:3.9.6-amazoncorretto-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Запуск
FROM amazoncorretto:17-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Ограничение памяти для стабильной работы на бесплатном тарифе Render
ENTRYPOINT ["java", "-Xmx320m", "-Xss512k", "-jar", "app.jar"]