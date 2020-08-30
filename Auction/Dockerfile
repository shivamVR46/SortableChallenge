FROM openjdk:8-alpine

WORKDIR /
ADD challenge.jar challenge.jar
COPY input.json input.json
COPY config.json config.json
COPY lib lib
CMD ["java", "-jar", "challenge.jar"]
