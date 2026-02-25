# Stage 1: Сборка
FROM maven:3.9.6-amazoncorretto-17 AS build
WORKDIR /app

# Копируем всё содержимое репозитория в /app
COPY . .

# Переходим в папку, где реально лежит pom.xml
WORKDIR /app/Tapsyrys

# Собираем проект
RUN mvn clean package -DskipTests

# Stage 2: Запуск
FROM amazoncorretto:17-alpine
WORKDIR /app

# Копируем собранный jar из папки target внутри подпапки Tapsyrys
COPY --from=build /app/Tapsyrys/target/*.jar app.jar

ENTRYPOINT ["java", "-Xmx320m", "-Xss512k", "-jar", "app.jar"]