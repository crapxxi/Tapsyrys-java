# Stage 1: Сборка
FROM maven:3.9.6-amazoncorretto-17 AS build
WORKDIR /app
COPY . .

# Собираем проект прямо в корне
RUN mvn clean package -DskipTests

# Stage 2: Запуск
FROM amazoncorretto:17-alpine
WORKDIR /app

# Забираем jar-файл из папки target
COPY --from=build /app/target/*.jar app.jar

# Лимит памяти для Render
ENTRYPOINT ["java", "-Xmx300m", "-Xss512k", "-jar", "app.jar"]