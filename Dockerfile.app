FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
WORKDIR /app
ARG JAR_FILE=/build/libs/gerenciamento-pedidos-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app/gerenciamento-pedidos.jar
ENTRYPOINT ["java", "-jar", "gerenciamento-pedidos.jar"]