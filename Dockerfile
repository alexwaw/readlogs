FROM openjdk:12-alpine

COPY target/app-*.jar /read-app.jar

CMD ["java", "-jar", "/read-app.jar"]