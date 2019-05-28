FROM openjdk:11-jre-slim-stretch

COPY "/target/resourceServer-0.0.1-SNAPSHOT.jar" "/opt"

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/opt/resourceServer-0.0.1-SNAPSHOT.jar" ]
