FROM openjdk:11-jdk-alpine AS builder

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew clean build

FROM openjdk:8-jdk-alpine

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE [8080,8090]

ENTRYPOINT ["java","-jar","app.jar"]