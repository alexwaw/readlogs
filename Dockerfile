FROM openjdk:12-alpine

COPY target/app-1.0-SNAPSHOT-jar-with-dependencies.jar /read-app.jar

CMD ["java", "-jar", "/read-app.jar"]