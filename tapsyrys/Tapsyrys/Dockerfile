FROM maven:3.9.6-amazoncorretto-17 AS build
WORKDIR /app
COPY . .

# Эта команда покажет нам структуру папок в логах Render
RUN find . -maxdepth 3 -name "pom.xml"

# Просто чтобы не падало сразу, попробуем найти и запустить
RUN mvn -f $(find . -name pom.xml) clean package -DskipTests

FROM amazoncorretto:17-alpine
WORKDIR /app
COPY --from=build /app/**/target/*.jar app.jar
ENTRYPOINT ["java", "-Xmx320m", "-jar", "app.jar"]