FROM gradle:jdk11
VOLUME /tmp
COPY . /app

WORKDIR /app
USER root

ENV GRADLE_HOME=/usr/bin/gradle
ENV PATH=$PATH:$GRADLE_HOME/bin

RUN gradle -s bootJar

ENTRYPOINT ["/usr/bin/java"]
EXPOSE 8080
CMD ["-jar", "build/libs/tokenapi-0.1.0.jar"]
