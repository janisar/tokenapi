FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=build/libs/tokenapi-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar

ENTRYPOINT ["/usr/bin/java"]

EXPOSE 8082

CMD ["-jar", "app.jar"]