FROM eclipse-temurin:20-jdk-alpine
VOLUME /tmp
COPY target/app.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]