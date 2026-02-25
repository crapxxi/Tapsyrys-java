# Stage 1: Сборка
FROM maven:3.9.6-amazoncorretto-17 AS build
WORKDIR /app
COPY . .

# Собираем проект, заходя в ту самую папку Tapsyrys
RUN mvn -f Tapsyrys/pom.xml clean package -DskipTests

# Stage 2: Запуск
FROM amazoncorretto:17-alpine
WORKDIR /app

# Копируем файл по прямому пути.
# Название файла в target обычно совпадает с artifactId из pom.xml
COPY --from=build /app/Tapsyrys/target/*.jar app.jar

# Лимит памяти для бесплатного тарифа Render
ENTRYPOINT ["java", "-Xmx320m", "-Xss512k", "-jar", "app.jar"]