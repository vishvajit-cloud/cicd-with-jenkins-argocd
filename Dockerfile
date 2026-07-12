FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY target/simple-app.jar /app/simple-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/simple-app.jar"]
