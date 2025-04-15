FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/springboot-0.0.1-SNAPSHOT.jar app.jar

ENV JAVA_TOOL_OPTIONS="-Duser.language=en -Duser.country=US"

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "app.jar"]
