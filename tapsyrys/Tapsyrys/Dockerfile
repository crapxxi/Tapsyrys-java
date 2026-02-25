# Stage 1: Сборка
FROM maven:3.9.6-amazoncorretto-17 AS build
WORKDIR /app
COPY . .

# 1. Сам ищет pom.xml (где бы он ни был) и запускает сборку
RUN POM_PATH=$(find . -name "pom.xml" | head -n 1) && \
    mvn -f "$POM_PATH" clean package -DskipTests

# 2. Сам ищет готовый .jar в папке target и перекладывает его в корень под именем app.jar
RUN JAR_PATH=$(find . -path "*/target/*.jar" ! -name "*plain.jar" | head -n 1) && \
    cp "$JAR_PATH" /app/app.jar

# Stage 2: Запуск
FROM amazoncorretto:17-alpine
WORKDIR /app

# Забираем уже подготовленный файл с точного места
COPY --from=build /app/app.jar .

# Лимит памяти для бесплатного тарифа Render
ENTRYPOINT ["java", "-Xmx300m", "-Xss512k", "-jar", "app.jar"]