# Stage 1: Build with Maven
FROM maven:3.8.7-openjdk-8 AS builder
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests



# Stage 2: Run with JDK only
FROM openjdk:8-jdk-alpine
WORKDIR /app
ENV TZ=Asia/Bangkok
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/Asia/Bangkok /etc/localtime && \
    echo "Asia/Bangkok" > /etc/timezone && \
    apk del tzdata


COPY --from=builder /app/target/AttableBackendGraphQL-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]