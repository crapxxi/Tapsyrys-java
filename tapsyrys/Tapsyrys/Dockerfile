# Stage 1: Сборка
FROM maven:3.9.6-amazoncorretto-17 AS build
WORKDIR /app
COPY . .

# Собираем проект, указывая точный путь к pom.xml
RUN mvn -f Tapsyrys/pom.xml clean package -DskipTests

# Stage 2: Запуск
FROM amazoncorretto:17-alpine
WORKDIR /app

# Копируем файл из конкретной папки.
# ВНИМАНИЕ: Если проект называется Tapsyrys, jar будет лежать там.
COPY --from=build /app/Tapsyrys/target/*.jar /app/app.jar

# Эта команда выведет список файлов в лог при запуске, чтобы мы проверили наличие
RUN ls -la /app/

# Лимит памяти
ENTRYPOINT ["java", "-Xmx300m", "-Xss512k", "-jar", "/app/app.jar"]