# Stage 1: Сборка
FROM maven:3.9.6-amazoncorretto-17 AS build
WORKDIR /app
COPY . .

# Ищем pom.xml в любой подпапке и запускаем сборку именно там
RUN mvn -f $(find . -name "pom.xml" | head -n 1) clean package -DskipTests

# Stage 2: Запуск
FROM amazoncorretto:17-alpine
WORKDIR /app

# Копируем созданный JAR файл, где бы он ни лежал после сборки
COPY --from=build /app/**/target/*.jar app.jar

ENTRYPOINT ["java", "-Xmx320m", "-Xss512k", "-jar", "app.jar"]