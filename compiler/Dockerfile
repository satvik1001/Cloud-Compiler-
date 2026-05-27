FROM eclipse-temurin:17

WORKDIR /app

COPY target/compiler-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9090

ENTRYPOINT ["java","-jar","app.jar"]