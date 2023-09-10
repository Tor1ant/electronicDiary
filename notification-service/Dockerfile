FROM amazoncorretto:17-alpine-jdk
COPY target/*.jar app.jar
CMD ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]