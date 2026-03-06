FROM maven:3.9.6-amazoncorretto-17 AS build
WORKDIR /app
COPY . .

RUN POM_PATH=$(find . -name "pom.xml" | head -n 1) && \
    mvn -f "$POM_PATH" clean package -DskipTests

RUN JAR_PATH=$(find . -path "*/target/*.jar" ! -name "*plain.jar" | head -n 1) && \
    cp "$JAR_PATH" /app/app.jar

FROM amazoncorretto:17-alpine
WORKDIR /app

COPY --from=build /app/app.jar .

ENTRYPOINT ["java", "-Xmx300m", "-Xss512k", "-jar", "app.jar"]