FROM gradle:jdk17

VOLUME /tmp
WORKDIR /app

COPY . .

RUN gradle app:api:clean app:api:build

ARG JAR_FILE=/app/api/build/libs/api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app/api.jar

ENTRYPOINT ["java", "-jar", "api.jar"]