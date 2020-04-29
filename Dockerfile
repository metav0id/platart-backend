FROM adoptopenjdk/openjdk11
COPY target/demo-0.0.1-SNAPSHOT.jar /usr/src/app.jar
WORKDIR /usr/src/
CMD ["java", "-jar", "-Dspring.profiles.active=default", "app.jar"]
