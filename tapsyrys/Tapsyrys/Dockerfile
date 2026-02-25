# Stage 1: Сборка
FROM maven:3.9.6-amazoncorretto-17 AS build
WORKDIR /app
COPY . .

# Собираем проект там, где лежит pom.xml
RUN mvn -f $(find . -name pom.xml) clean package -DskipTests

# Stage 2: Запуск
FROM amazoncorretto:17-alpine
WORKDIR /app

# Ищем созданный jar файл и копируем его как app.jar
# Мы используем wildcard, чтобы не зависеть от названия папки Tapsyrys
COPY --from=build /app/**/target/*.jar app.jar

# Проверяем, что файл скопировался (для логов)
RUN ls -la app.jar

ENTRYPOINT ["java", "-Xmx320m", "-Xss512k", "-jar", "app.jar"]