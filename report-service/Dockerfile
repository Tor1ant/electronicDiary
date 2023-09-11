FROM amazoncorretto:17-alpine-jdk
RUN apk add --no-cache fontconfig ttf-dejavu
COPY target/*.jar app.jar
CMD ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]