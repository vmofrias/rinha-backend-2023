FROM openjdk:20-jdk-slim

RUN mkdir -p /app

WORKDIR /app

COPY ./target/rinhabackend2023-0.0.1-SNAPSHOT.jar rinha.jar

EXPOSE 9999

ENTRYPOINT [ "java", "-XX:+UseParallelGC", "-XX:MaxRAMPercentage=75", "-jar", "./rinha.jar" ]