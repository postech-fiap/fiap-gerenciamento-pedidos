FROM gradle:jdk17 as gradleimage
COPY . /home/gradle/source
WORKDIR /home/gradle/source
RUN gradle clean app:api:build

FROM eclipse-temurin:17-jdk-alpine
COPY --from=gradleimage /home/gradle/source/app/api/build/libs/api-0.0.1-SNAPSHOT.jar /app/
WORKDIR /app
ENTRYPOINT ["java", "-jar", "api-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080
